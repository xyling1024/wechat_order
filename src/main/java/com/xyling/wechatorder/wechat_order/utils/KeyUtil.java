package com.xyling.wechatorder.wechat_order.utils;

import java.util.Random;

/**
 * 创建主键的工具类
 * Created by: xyling
 * 2018-03-02 16:56
 */
public class KeyUtil {

    public static synchronized String genUniqueKey() {
//        new Random().nextInt(90) + 10; // 保证两位数
        Integer num = new Random().nextInt(900000) + 100000;  // 保证6位数
        return System.currentTimeMillis() + String.valueOf(num);
    }
}
