package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by: xyling
 * 2018-03-01 13:40
 */
public interface ProductInfoRepository
        extends JpaRepository<ProductInfo, String>, JpaSpecificationExecutor<ProductInfo> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
