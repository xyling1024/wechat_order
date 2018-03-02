package com.xyling.wechatorder.wechat_order.service.impl;

import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.query.ProductQuery;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-01 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("222222");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        Page<ProductInfo> page = productService.findUpAll(0, 10);
//        System.err.println(
//                "TotalElements: " + page.getTotalElements() +
//                ", TotalPages: " + page.getTotalPages() +
//                ", Content: " + page.getContent());
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void findDownAll() throws Exception {
        Page<ProductInfo> page = productService.findDownAll(0, 10);
//        System.err.println(
//                "TotalElements: " + page.getTotalElements() +
//                ", TotalPages: " + page.getTotalPages() +
//                ", Content: " + page.getContent());
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void findAllByPage() throws Exception {
        Page<ProductInfo> page = productService.findAllByPage(0, 10);
//        System.err.println(
//                "TotalElements: " + page.getTotalElements() +
//                ", TotalPages: " + page.getTotalPages() +
//                ", Content: " + page.getContent());
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void findAllByPageWithCriteria() throws Exception {
        ProductQuery query = new ProductQuery();
        query.setProductName("粥");
        query.setProductPrice("0-5");
        Page<ProductInfo> page = productService.findAllByPageWithCriteria(0, 10, query);
//        System.err.println(
//                "TotalElements: " + page.getTotalElements() +
//                ", TotalPages: " + page.getTotalPages() +
//                ", Content: " + page.getContent());
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = productService.save(
                new ProductInfo("222222", "八宝粥", new BigDecimal(3), 100, "甜甜的, 很暖心", "http://xying1024.jpg", 1, 5)
        );
        Assert.assertNotNull(productInfo);
    }

}