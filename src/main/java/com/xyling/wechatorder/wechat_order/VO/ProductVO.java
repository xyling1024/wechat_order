package com.xyling.wechatorder.wechat_order.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品VO
 * Created by: xyling
 * 2018-03-01 17:58
 */
@Data
@NoArgsConstructor
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoList;

    public ProductVO(String categoryName, Integer categoryType, List<ProductInfoVO> productInfoList) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.productInfoList = productInfoList;
    }
}
