package com.xyling.wechatorder.wechat_order.controller;

import com.xyling.wechatorder.wechat_order.DTO.OrderDTO;
import com.xyling.wechatorder.wechat_order.enums.ResultEnum;
import com.xyling.wechatorder.wechat_order.exception.SellException;
import com.xyling.wechatorder.wechat_order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by: xyling
 * 2018-03-10 15:26
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        if ( page < 1 ) {
            page = 1;
        }
        Page<OrderDTO> orderDTOPage = orderService.findList(new PageRequest(page - 1, size));
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO one = orderService.findOne(orderId);
            orderService.cancel(one);
        } catch (SellException e) {
            log.error("[卖家端取消订单] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("msg", ResultEnum.CANCEL_ORDER_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("commons/success", map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e) {
            log.error("[卖家端订单详情] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO one = orderService.findOne(orderId);
            orderService.finish(one);
        } catch (SellException e) {
            log.error("[卖家端完结订单] {}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("commons/error", map);
        }
        map.put("msg", ResultEnum.FINISH_ORDER_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("commons/success", map);
    }

}
