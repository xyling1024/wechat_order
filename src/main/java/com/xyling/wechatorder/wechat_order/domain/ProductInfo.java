package com.xyling.wechatorder.wechat_order.domain;

import com.xyling.wechatorder.wechat_order.enums.ProductStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by: xyling
 * 2018-03-01 13:33
 */
@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDesc;

    private String productIcon;

    private Integer productStatus = ProductStatusEnum.UP.getStatus();

    private Integer categoryType;

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDesc, String productIcon, Integer productStatus, Integer categoryType) {
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
