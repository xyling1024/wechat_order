package com.xyling.wechatorder.wechat_order.utils;

/**
 * Created by: xyling
 * 2018-03-14 15:56
 */
public class MathUtil {

    public static final Double DOUBLE_RANGE = 0.01; // 精度

    /**
     * 判断两个double类型值是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if ( result < DOUBLE_RANGE ) {
            return true;
        }
        return false;
    }
}
