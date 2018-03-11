package com.xyling.wechatorder.wechat_order.service.impl;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.enums.OrderStatusEnum;
import com.xyling.wechatorder.wechat_order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-03 22:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    private static final String BUYER_OPENID = "service_test";

    private static final String ORDER_ID = "1520090059949724382";

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerPhone("18868882211");
        orderDTO.setBuyerAddress("慕课网总部");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setOrderDetailList(Arrays.asList(new OrderDetail("111111", 1), new OrderDetail("222222", 2)));
        orderDTO = orderService.create(orderDTO);
        Assert.assertNotNull(orderDTO);

    }

    @Test
    public void findOne() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        System.err.println(orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findListByOpenid() throws Exception {
        Page<OrderDTO> orderDTOPage = orderService.findListByOpenid(BUYER_OPENID, new PageRequest(0, 10));
        Assert.assertNotEquals(0, orderDTOPage.getContent());
    }

    @Test
    public void findList() throws Exception {
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(0, 10));
        Assert.assertTrue("订单列表查询", orderDTOPage.getContent().size() > 0);
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderDTO = orderService.cancel(orderDTO);
        Assert.assertEquals(orderDTO.getOrderStatus(), OrderStatusEnum.CANCEL.getStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderDTO = orderService.finish(orderDTO);
        Assert.assertEquals(orderDTO.getOrderStatus(), OrderStatusEnum.FINISHED.getStatus());
    }

    @Test
    public void pay() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        orderDTO = orderService.pay(orderDTO);
        Assert.assertEquals(orderDTO.getPayStatus(), PayStatusEnum.SUCCESS.getStatus());
    }

}