package com.xyling.wechatorder.wechat_order.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by: xyling
 * 2018-03-05 13:00
 */
@Slf4j
public class JacksonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 默认格式: 键值对必须都是双引号包着的
        // 忽略单双引格式检查
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /**
     * 将json字符串转换为Map、List<Map>、javaBean等类型
     * @param jsonString
     * @param simpleClass
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String jsonString, Class<T> simpleClass) {
        T result = null;
        try {
            result = mapper.readValue(jsonString, simpleClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将json字符串转换为List<javaBean>、Map<String, javaBean>等类型
     *      如果是ArrayList<javaBean>, 那么传入jsonString, ArrayList.class, javaBean.class
     *      如果是HashMap<String, javaBean>, 那么传入jsonString, HashMap.class, String.class, javaBean.class
     * @param jsonString
     * @param collectionClass
     * @param elementClasses
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String jsonString, Class<T> collectionClass, Class<?>... elementClasses) {
        T result = null;
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            result = mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String object2Json(Object data) {
        String result = "";
        try {
            result = mapper.writeValueAsString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
