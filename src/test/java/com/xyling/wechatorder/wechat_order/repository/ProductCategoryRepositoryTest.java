package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-02-28 16:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOne() {
        ProductCategory category = repository.findOne(1);
        Assert.assertNotNull(category);
    }

    @Test
//    @Transactional  // 方法执行完成后回滚
    public void saveInsert() {
        ProductCategory category = repository.save(new ProductCategory("主食", 5));
        Assert.assertNotNull(category);
    }

    @Test
//    @Transactional
    public void saveUpdate() {
        ProductCategory category = repository.findOne(1);
        category.setCategoryName("男生最爱");
        category = repository.save(category);
        Assert.assertNotNull(category);
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> categoryList = repository.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4, 5));
        Assert.assertNotEquals(0, categoryList.size());
    }

}