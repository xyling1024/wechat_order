package com.xyling.wechatorder.wechat_order.domain;

import com.xyling.wechatorder.wechat_order.enums.OrderStatusEnum;
import com.xyling.wechatorder.wechat_order.enums.PayStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by: xyling
 * 2018-03-02 11:20
 */
@Entity
@Data
@NoArgsConstructor
@DynamicUpdate
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnum.NEW.getStatus();  // 新订单

    private Integer payStatus = PayStatusEnum.WAIT.getStatus();  // 未支付

    private Date createTime;

    private Date updateTime;

//    @javax.persistence.Transient
//    private List<OrderDetail> orderDetailList;

    public OrderMaster(String orderId, String buyerName, String buyerPhone, String buyerAddress, String buyerOpenid, BigDecimal orderAmount) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
    }
}
