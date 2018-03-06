package com.xyling.wechatorder.wechat_order.service;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by: xyling
 * 2018-03-05 19:23
 */
public interface BuyerService {

    /**
     * 查询买家单个订单
     * 校验订单所有者是否是当前用户
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO findOrderOne(String openid, String orderId);

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDTO cancel(String openid, String orderId);
}
