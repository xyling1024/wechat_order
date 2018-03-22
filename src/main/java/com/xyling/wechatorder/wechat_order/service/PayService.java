package com.xyling.wechatorder.wechat_order.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;

/**
 * Created by: xyling
 * 2018-03-13 16:08
 */
public interface PayService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 异步通知
     * @param notifyData
     */
    PayResponse notify(String notifyData);

    /**
     * 退款
     * @param orderDTO
     * @return
     */
    RefundResponse refund(OrderDTO orderDTO);
}
