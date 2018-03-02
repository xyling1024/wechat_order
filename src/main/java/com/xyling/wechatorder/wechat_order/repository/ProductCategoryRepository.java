package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by: xyling
 * 2018-02-28 16:51
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
