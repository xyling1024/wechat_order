package com.xyling.wechatorder.wechat_order.service.impl;

import com.xyling.wechatorder.wechat_order.DTO.CartDTO;
import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.converter.OrderMaster2OrderDTOConverter;
import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.domain.OrderMaster;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.OrderStatusEnum;
import com.xyling.wechatorder.wechat_order.enums.PayStatusEnum;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.repository.OrderDetailRepository;
import com.xyling.wechatorder.wechat_order.repository.OrderMasterRepository;
import com.xyling.wechatorder.wechat_order.service.OrderService;
import com.xyling.wechatorder.wechat_order.service.ProductService;
import com.xyling.wechatorder.wechat_order.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: xyling
 * 2018-03-02 16:40
 */
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        // 这里我们重新创建了一个OrderDTO类, 因为保存订单时, 肯定需要保存订单详情列表.

        String orderId = KeyUtil.genUniqueKey();    // 订单ID
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);   // 订单总金额

// 目标一: 保存orderDetailList
        // 查询商品详情
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            // 判断商品是否存在
            if ( productInfo == null ) {
                throw new SellException(ResultEnum.PRODUCT_NO_EXIST);
            }
            // 计算订单总金额. 注意: 凡金额字段, 一定不能由前端传递, 必须从数据库查询得出!!
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 注意: 一定要先写BeanUtils.copyProperties(srcObj, defObj)方法, 再为对象设置值.
                // 否则, 我们设置进去的值, 会被BeanUtils的copyProperties(srcObj, defObj)方法覆盖.
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);

        }

// 目标二: 保存orderMaster
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        orderDTO.setOrderAmount(orderAmount);
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getStatus());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterRepository.save(orderMaster);

// 目标三: 减库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                    a -> new CartDTO(a.getProductId(), a.getProductQuantity())
            ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        // 查询orderMaster
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if ( orderMaster == null ) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 查询orderDetailList
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        // 封装到orderDTO
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findListByOpenid(String buyerOpenid, Pageable pageable) {
        // 查询orderMasterList
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        // 转化为orderDTOList
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(
                OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent()),
                pageable,
                orderMasterPage.getTotalElements()
        );
        return orderDTOPage;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 判断订单状态
        if( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus()) ) {
            log.error("[取消订单] 订单状态不正确: orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if( !orderMaster.getOrderStatus().equals(OrderStatusEnum.CANCEL.getStatus()) ) {
            log.error("[取消订单] 更新失败: orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返回库存
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        if ( CollectionUtils.isEmpty(orderDetailList) ) {
            log.error("[取消订单] 订单详情不存在: orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map( e -> new CartDTO(e.getProductId(), e.getProductQuantity() ))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        // 若已付款, 退款
        if ( orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getStatus()) ) {
            // TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // 判断订单状态
        if( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus()) ) {
            log.error("[完结订单] 订单状态不正确: orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if( !orderMaster.getOrderStatus().equals(OrderStatusEnum.FINISHED.getStatus()) ) {
            log.error("[完结订单] 更新失败: orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        // 判断订单状态
        if( !orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getStatus()) ) {
            log.error("[支付订单] 订单状态不正确: orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if( !orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getStatus()) ) {
            log.error("[支付订单] 支付状态不正确: orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getStatus());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster = orderMasterRepository.save(orderMaster);
        if( !orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getStatus()) ) {
            log.error("[支付订单] 更新失败: orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        // 查询orderMasterList
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        // 转化为orderDTOList
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(
                OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent()),
                pageable,
                orderMasterPage.getTotalElements()
        );
        return orderDTOPage;
    }
}
