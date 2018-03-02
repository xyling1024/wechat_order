package com.xyling.wechatorder.wechat_order.service.impl;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by: xyling
 * 2018-03-01 13:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl productCategoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory category = productCategoryService.findOne(7);
        Assert.assertNotNull(category);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> list = productCategoryService.findAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list = productCategoryService.findByCategoryTypeIn(Arrays.asList(3, 4));
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory category = productCategoryService.save(new ProductCategory("零食小吃", 4));
        Assert.assertNotNull(category);
    }

}