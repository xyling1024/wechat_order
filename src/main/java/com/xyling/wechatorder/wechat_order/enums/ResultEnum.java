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

    PARAM_ERROR(5, "参数不正确"),

    PRODUCT_NO_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_UPDATE_FAIL(15, "订单更新失败"),

    ORDER_PAY_STATUS_ERROR(16, "订单支付状态不正确"),

    CART_EMPTY(17, "购物车为空"),

    ORDER_OWNER_ERROR(18, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(19, "微信公众号错误"),

    ENCODED_ERROR(20, "编码错误"),

    CANCEL_ORDER_SUCCESS(21, "取消订单成功"),

    FINISH_ORDER_SUCCESS(22, "完结订单成功"),

    PRODUCT_STATUS_ERROR(23, "商品状态错误"),

    ONSALE_PRODUCT_SUCCESS(24, "商品上架成功"),

    OFFSALE_PRODUCT_SUCCESS(25, "商品下架成功"),

    PRODUCT_CATEGORY_NO_EXIST(26, "商品类目不存在"),

    WXPAY_NOTIFY_VERIFY_ERROR(26, "微信异步通知金额校验错误"),

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
