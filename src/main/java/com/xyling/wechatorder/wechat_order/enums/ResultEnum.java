package com.xyling.wechatorder.wechat_order.enums;

import lombok.Getter;

/**
 * http请求返回的result枚举对象
 * Created by: xyling
 * 2018-03-01 20:29
 */
@Getter
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILED(-1, "失败"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
