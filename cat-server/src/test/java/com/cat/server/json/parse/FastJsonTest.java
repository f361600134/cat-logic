package com.cat.server.json.parse;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * 测试类
 * </p>
 *
 * @author LiYangDi
 * @since 2019/11/20
 */
public class FastJsonTest {

    public static void main(String... args) {
        final String json = "{\"time\":\"2019-12-23 20:18:02.111111111\", \"name\":\"seliote\"}";
        Pojo pojo = JSONObject.parseObject(json, Pojo.class);
        System.out.println(pojo);
    }
}