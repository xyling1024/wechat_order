package com.xyling.wechatorder.wechat_order.service.impl;

import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.ProductStatusEnum;
import com.xyling.wechatorder.wechat_order.query.ProductQuery;
import com.xyling.wechatorder.wechat_order.repository.ProductInfoRepository;
import com.xyling.wechatorder.wechat_order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: xyling
 * 2018-03-01 14:07
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public Page<ProductInfo> findAllByPage(Integer page, Integer size) {
        return repository.findAll(
                new PageRequest(page, size, Sort.Direction.ASC, "productId")
        );
    }

    @Override
    public Page<ProductInfo> findAllByPageWithCriteria(Integer page, Integer size, ProductQuery productQuery) {
        return repository.findAll(new Specification<ProductInfo>(){
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(null!=productQuery.getProductName()&&!"".equals(productQuery.getProductName())){
                    list.add(criteriaBuilder.like(root.get("productName").as(String.class), "%" + productQuery.getProductName() + "%"));
                }
                if(null!=productQuery.getProductPrice()&&!"".equals(productQuery.getProductPrice())){
                    String[] split = productQuery.getProductPrice().split("-");
                    list.add(criteriaBuilder.between(root.get("productPrice").as(String.class), split[0], split[1]));
                }
                if(null!=productQuery.getProductStock()&&!"".equals(productQuery.getProductStock())){
                    String[] split = productQuery.getProductStock().split("-");
                    list.add(criteriaBuilder.between(root.get("productStock").as(String.class), split[0], split[1]));
                }
                if(null!=productQuery.getProductDesc()&&!"".equals(productQuery.getProductDesc())){
                    list.add(criteriaBuilder.like(root.get("productDesc").as(String.class), "%" + productQuery.getProductDesc() + "%"));
                }
                if(null!=productQuery.getProductIcon()&&!"".equals(productQuery.getProductIcon())){
                    list.add(criteriaBuilder.like(root.get("productIcon").as(String.class), "%" + productQuery.getProductIcon() + "%"));
                }
                if(null!=productQuery.getProductStatus()&&!"".equals(productQuery.getProductStatus())){
                    list.add(criteriaBuilder.equal(root.get("productStatus").as(Integer.class), productQuery.getProductStatus()));
                }
                if(null!=productQuery.getCategoryType()&&!"".equals(productQuery.getCategoryType())){
                    list.add(criteriaBuilder.equal(root.get("categoryType").as(Integer.class), productQuery.getCategoryType()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, new PageRequest(page, size, Sort.Direction.ASC, "productId"));
    }

    @Override
    public List<ProductInfo> findUpAll() {
           return repository.findByProductStatus(ProductStatusEnum.UP.getStatus());
    }

    @Override
    public Page<ProductInfo> findUpAll(Integer page, Integer size) {
        return findAllByPageWithCriteria(page, size, new ProductQuery(null, null, null, null, null, null, ProductStatusEnum.UP.getStatus(), null));
    }

    @Override
    public List<ProductInfo> findDownAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getStatus());
    }

    @Override
    public Page<ProductInfo> findDownAll(Integer page, Integer size) {
        return findAllByPageWithCriteria(page, size,
                new ProductQuery(null, null, null, null, null, null, ProductStatusEnum.DOWN.getStatus(), null));
//        return repository.findByProductStatus(ProductStatusEnum.DOWN.getStatus());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
