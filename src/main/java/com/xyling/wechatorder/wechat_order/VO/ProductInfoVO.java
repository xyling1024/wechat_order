package com.xyling.wechatorder.wechat_order.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by: xyling
 * 2018-03-01 18:02
 */
@Data
@NoArgsConstructor
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("desc")
    private String productDesc;

    @JsonProperty("icon")
    private String productIcon;

}
