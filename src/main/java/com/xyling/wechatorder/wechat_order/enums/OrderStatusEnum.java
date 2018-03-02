package com.xyling.wechatorder.wechat_order.enums;

import lombok.Getter;

/**
 * Created by: xyling
 * 2018-02-28 16:14
 * 订单状态枚举类
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer status;

    private String message;

    OrderStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
