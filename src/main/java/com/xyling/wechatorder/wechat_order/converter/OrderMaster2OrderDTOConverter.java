package com.xyling.wechatorder.wechat_order.converter;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.domain.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: xyling
 * 2018-03-04 12:43
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        List<OrderDTO> orderDTOList = orderMasterList.stream()
                .map( e -> convert(e) ).collect(Collectors.toList());
        return orderDTOList;
    }

}
