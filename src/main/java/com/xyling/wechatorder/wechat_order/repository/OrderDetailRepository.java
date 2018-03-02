package com.xyling.wechatorder.wechat_order.repository;

import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import com.xyling.wechatorder.wechat_order.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by: xyling
 * 2018-02-28 16:51
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String buyerOpenid);
}
