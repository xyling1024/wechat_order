package com.xyling.wechatorder.wechat_order.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物车
 * Created by: xyling
 * 2018-03-02 16:23
 */
@Data
@NoArgsConstructor
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
