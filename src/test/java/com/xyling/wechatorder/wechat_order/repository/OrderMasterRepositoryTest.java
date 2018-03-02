package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-02 11:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "xyling1024";

    @Test
    public void save() throws Exception {
        OrderMaster orderMaster = repository.save(new OrderMaster("123", "小仙女", "13434232432", "北京市昌平区", "xyling1024", new BigDecimal(12.0)));
        Assert.assertNotNull(orderMaster);
    }

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        Page<OrderMaster> page = repository.findByBuyerOpenid(OPENID, new PageRequest(0, 10));
        System.err.println(
                "TotalElements: " + page.getTotalElements() +
                ", TotalPages: " + page.getTotalPages() +
                ", Content: " + page.getContent());
        Assert.assertNotEquals(0, page.getContent().size());
    }

}