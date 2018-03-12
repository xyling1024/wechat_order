package com.xyling.wechatorder.wechat_order.enums;

import lombok.Getter;

/**
 * Created by: xyling
 * 2018-02-28 16:14
 * 商品状态枚举类
 */
@Getter
public enum ProductStatusEnum implements StatusEnum<Integer> {
    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer status;

    private String message;

    ProductStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
