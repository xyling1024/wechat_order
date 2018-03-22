package com.xyling.wechatorder.wechat_order.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 廖师兄
 * 2017-07-04 01:30
 */
public class GsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
