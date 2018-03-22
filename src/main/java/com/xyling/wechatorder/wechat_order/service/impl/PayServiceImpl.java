package com.xyling.wechatorder.wechat_order.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.service.OrderService;
import com.xyling.wechatorder.wechat_order.service.PayService;
import com.xyling.wechatorder.wechat_order.utils.GsonUtil;
import com.xyling.wechatorder.wechat_order.utils.JacksonUtil;
import com.xyling.wechatorder.wechat_order.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by: xyling
 * 2018-03-13 16:08
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    private static final String ORDER_NAME = "仙女棒的购买";

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(0.01);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] 发起支付, request = {}", GsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付] 发起支付, response = {}", GsonUtil.toJson(payResponse));

        return payResponse;
    }

/*  <xml>
        <appid><![CDATA[wxd898fcb01713c658]]></appid>
        <bank_type><![CDATA[CFT]]></bank_type>
        <cash_fee><![CDATA[1]]></cash_fee>
        <fee_type><![CDATA[CNY]]></fee_type>
        <is_subscribe><![CDATA[Y]]></is_subscribe>
        <mch_id><![CDATA[1483469312]]></mch_id>
        <nonce_str><![CDATA[Y28khfzaqjejHeGs]]></nonce_str>
        <openid><![CDATA[oTgZpwV22-ZRcsQekn8qKX38Bhl4]]></openid>
        <out_trade_no><![CDATA[1520232067736585026]]></out_trade_no>
        <result_code><![CDATA[SUCCESS]]></result_code>
        <return_code><![CDATA[SUCCESS]]></return_code>
        <sign><![CDATA[30C4D32D5959FEF3A98C8F4D6342961A]]></sign>
        <time_end><![CDATA[20180314153658]]></time_end>
        <total_fee>1</total_fee>
        <trade_type><![CDATA[JSAPI]]></trade_type>
        <transaction_id><![CDATA[4200000074201803148540415297]]></transaction_id>
    </xml>  */
    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知, {}", GsonUtil.toJson(payResponse));

        // 1. 签名验证
        // 2. 验证支付状态
        // 以上两步由SDK帮我们做了.

        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        // 判断订单是否存在
        if ( orderDTO == null ) {
            log.error("[微信支付] 异步通知 订单不存在, orderId = {}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 3. 校验订单金额是否一致
        if ( !MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue()) ) {
            log.error("[微信支付] 异步通知 订单金额不一致, orderId = {}, 微信通知金额 = {}, 系统金额 = {}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount().doubleValue());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_VERIFY_ERROR);
        }

        // 4. 验证支付人与订单人是否相同(非必须, 具体看业务需求)

        // 5. 更改订单支付状态, (记录订单支付流水号)
        orderDTO = orderService.pay(orderDTO);
        return payResponse;

        // 6. 回复微信

    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款], request =  {}", refundRequest);
        RefundResponse refund = bestPayService.refund(refundRequest);
        log.info("[微信退款], response =  {}", refund);
        return refund;
    }
}
