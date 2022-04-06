package com.example.pan.util;

import com.alibaba.fastjson.JSONObject;
import com.example.pan.entity.User;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author daShen
 * @since 2022-04-05
 */
public class ResultHandler {

    public static String handler(JSONObject json, String code, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("json", json);
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        return jsonObject.toString();
    }

    public static String handler(JSONObject json, String code, String msg, List<User> list) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("json", json);
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        jsonObject.put("List", list);
        return jsonObject.toString();
    }
}
