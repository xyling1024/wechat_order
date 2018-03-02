package com.xyling.wechatorder.wechat_order.enums;

import lombok.Getter;

/**
 * Created by: xyling
 * 2018-02-28 16:14
 * 订单状态枚举类
 */
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer status;

    private String message;

    PayStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
