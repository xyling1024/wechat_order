package com.xyling.wechatorder.wechat_order.query;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by: xyling
 * 2018-03-01 15:31
 */
@Data
@NoArgsConstructor
public class ProductQuery {

    private String productId;

    private String productName;     // like

    private String productPrice;    // between xxx-xxx

    private String productStock;    // between xxx-xxx

    private String productDesc;     // like

    private String productIcon;     // like

    private Integer productStatus;  // equals

    private Integer categoryType;   // equals

    public ProductQuery(String productId, String productName, String productPrice, String productStock, String productDesc, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDesc = productDesc;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }
}
