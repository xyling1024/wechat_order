package com.xyling.wechatorder.wechat_order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by: xyling
 * 2018-02-28 16:51
 */
@Entity
//@Table(name = "product_category")
@Data   // getter/setter
@NoArgsConstructor  // 无参构造
@DynamicUpdate  // 动态更新     updateTime等字段, 由mysql维护
public class ProductCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
//
//    private Date createTime;
//
//    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
