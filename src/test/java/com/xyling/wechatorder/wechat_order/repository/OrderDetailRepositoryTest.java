package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-02 11:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void save() throws Exception {
        OrderDetail orderDetail = repository.save(new OrderDetail("22", "11", "111111", "皮蛋瘦肉粥", new BigDecimal(6.0), 1, "http://xyling1024.com"));
        Assert.assertNotNull(orderDetail);
    }
    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = repository.findByOrderId("11");
        Assert.assertNotEquals(0, orderDetailList.size());
    }

}