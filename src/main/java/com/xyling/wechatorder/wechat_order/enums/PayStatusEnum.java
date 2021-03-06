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
public enum PayStatusEnum implements StatusEnum<Integer> {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer status;

    private String message;
}
