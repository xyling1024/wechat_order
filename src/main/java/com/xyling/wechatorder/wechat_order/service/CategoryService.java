package com.xyling.wechatorder.wechat_order.service;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;

import java.util.List;

/**
 * Created by: xyling
 * 2018-03-01 13:16
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
