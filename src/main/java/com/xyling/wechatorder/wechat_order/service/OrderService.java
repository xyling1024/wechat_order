package com.xyling.wechatorder.wechat_order.service;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by: xyling
 * 2018-03-02 16:17
 */
public interface OrderService {

    /** 创建. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 根据openid查询订单列表. */
    Page<OrderDTO> findListByOpenid(String buyerOpenid, Pageable pageable);

    /** 查询订单列表. */
    Page<OrderDTO> findList(Pageable pageable);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO pay(OrderDTO orderDTO);

}
