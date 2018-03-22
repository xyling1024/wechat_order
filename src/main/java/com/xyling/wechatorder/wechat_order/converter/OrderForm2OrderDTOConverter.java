package com.xyling.wechatorder.wechat_order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by: xyling
 * 2018-03-04 19:42
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

//        List<OrderDetail> orderDetailList = JacksonUtil.json2Object(orderForm.getItems(), List.class, OrderDetail.class);
        List<OrderDetail> orderDetailList = new Gson().fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
