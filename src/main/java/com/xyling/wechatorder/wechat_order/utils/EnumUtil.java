package com.xyling.wechatorder.wechat_order.utils;

import com.xyling.wechatorder.wechat_order.enums.OrderStatusEnum;
import com.xyling.wechatorder.wechat_order.enums.StatusEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: xyling
 * 2018-03-11 9:48
 */
public class EnumUtil<T> {

    public static <T extends StatusEnum<Integer>> T getEnumMsgByStatus(Integer status, Class<T> enumClass) {
       /* for (T each : enumClass.getEnumConstants()) {
            if ( status.equals(each.getStatus()) ) {
                return each;
            }
        }
        //return null;*/

        List<T> collect = Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> status.equals(e.getStatus())).limit(1).collect(Collectors.toList());
        return collect.get(0);
    }
}
