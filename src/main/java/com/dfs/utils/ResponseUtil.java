package com.dfs.utils;

import com.dfs.model.ApiCodeEnum;
import com.dfs.response.Response;
/**
 * @author taoxy 2019/1/9
 */
public class ResponseUtil {

    public static String getResponse(Object content, ApiCodeEnum code) {
        Response outputParam = new Response();
        outputParam.setContent(content);
        outputParam.setStatus(code.getCode());
        outputParam.setMsg(code.getMsg());
        return JsonUtils.toJsonWithGson(outputParam);
    }

    public static String getResponse(ApiCodeEnum code) {
        Response outputParam = new Response();
        outputParam.setContent(new Object());
        outputParam.setStatus(code.getCode());
        outputParam.setMsg(code.getMsg());
        return JsonUtils.toJsonWithGson(outputParam);
    }

    public static String getResponse(String code, String msg, Object content) {
        Response outputParam = new Response();
        outputParam.setContent(content);
        outputParam.setStatus(code);
        outputParam.setMsg(msg);
        return JsonUtils.toJsonWithGson(outputParam);
    }

    public static String getResponse(String code, String msg) {
        Response outputParam = new Response();
        outputParam.setContent(new Object());
        outputParam.setStatus(code);
        outputParam.setMsg(msg);
        return JsonUtils.toJsonWithGson(outputParam);
    }

}
