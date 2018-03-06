package com.xyling.wechatorder.wechat_order.utils;

import com.xyling.wechatorder.wechat_order.domain.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by: xyling
 * 2018-03-05 14:12
 */
@Slf4j
public class JacksonUtilsTest {
    @Test
    public void json2Object() throws Exception {

        // 简单的json转换
        String jsonListStr = "[{productId:222222, productQuantity:1}]";
        List list = JacksonUtils.json2Object(jsonListStr, List.class);
        log.info("list = {}", list);
        log.info("list.get(0) instanceof Map? {}", list.get(0) instanceof Map);    // 默认转化出来为List<Map>类型

        // 复杂类型的json转换
        List orderDetailList = JacksonUtils.json2Object(jsonListStr, List.class, OrderDetail.class);
        log.info("orderDetailList = {}", orderDetailList);
        // 经过处理, 转化出来的是List<javaBean>类型
        log.info("orderDetailList.get(0) instanceof OrderDetail? {}", orderDetailList.get(0) instanceof OrderDetail);

       /* String listStr = JacksonUtils.object2Json(list);
        log.info("listStr = {}", listStr);

        String jsonMapStr = "{productId:222222, productQuantity:1}";
        Map map = JacksonUtils.json2Object(jsonMapStr, Map.class);
        log.info("map = {}", map);

        String mapStr = JacksonUtils.object2Json(map);
        log.info("mapStr = {}", mapStr);*/


    }

}