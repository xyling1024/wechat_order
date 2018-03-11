package com.xyling.wechatorder.wechat_order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by: xyling
 * 2018-02-28 16:14
 * 订单状态枚举类
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements StatusEnum<Integer> {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer status;

    private String message;
}
