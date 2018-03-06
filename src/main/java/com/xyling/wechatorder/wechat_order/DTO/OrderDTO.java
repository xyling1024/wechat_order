package com.xyling.wechatorder.wechat_order.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.utils.serializer.Date2LongSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单的数据传输对象
 * Created by: xyling
 * 2018-03-02 16:21
 */
@Data
@NoArgsConstructor
// 转json时忽略null值
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)    // 过时的用法
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList = new ArrayList<>();
            // 初始化, 这样在转json时即使没有值也会被转成"[]", 而不会是null

}
