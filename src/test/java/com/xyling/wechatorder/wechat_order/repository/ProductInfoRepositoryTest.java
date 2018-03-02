package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.ProductCategory;
import com.xyling.wechatorder.wechat_order.domain.ProductInfo;
import com.xyling.wechatorder.wechat_order.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-01 13:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findOne() {
        ProductInfo info = repository.findOne("111111");
        Assert.assertNotNull(info);
    }

    @Test
//    @Transactional  // 方法执行完成后回滚
    public void saveInsert() {
        ProductInfo info = repository.save(
                new ProductInfo("111111", "皮蛋瘦肉粥", new BigDecimal(5), 100, "很好喝的粥", "http://xying1024.jpg", 0, 5)
        );
        Assert.assertNotNull(info);
    }

    @Test
//    @Transactional
    public void saveUpdate() {
        ProductInfo info = repository.findOne("111111");
        info.setProductPrice(new BigDecimal(6));
        info = repository.save(info);
        Assert.assertEquals(6, info.getProductPrice().intValue());
    }

/*    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = repository.findByProductStatus(ProductStatusEnum.UP.getStatus());
        Assert.assertNotEquals(0, productInfoList.size());
    }*/

}