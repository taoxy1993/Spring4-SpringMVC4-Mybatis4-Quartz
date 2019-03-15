package com.dfs.controller.applite;


import com.dfs.annotation.IgnoreSecurity;
import com.dfs.model.ApiCodeEnum;
import com.dfs.model.WechatConfig;
import com.dfs.service.SenseAgroMemberService;
import com.dfs.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author taoxy 2018/12/12
 */

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private SenseAgroMemberService memberService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final RedisSingletonUtil redisUtil = new RedisSingletonUtil();

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @IgnoreSecurity
    public String loginIn(@RequestBody String param, HttpServletResponse res) {
        Map<String, Object> map = new HashMap();
        map.put("sessionId", "");
        try {
            String code = JsonUtils.getJsonStringParam(param, "code");
            String url = WechatConfig.openid_url + WechatConfig.appid + "&secret=" + WechatConfig.appsecret + "&js_code=" + code + "&grant_type=authorization_code";
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            String response = null;
            HttpResponse resp = httpclient.execute(httpget);
            HttpEntity entity = resp.getEntity();
            if (entity != null) {
                response = EntityUtils.toString(entity, "UTF-8").trim();
            }
            String sessionKey = JsonUtils.getJsonStringParam(response, "session_key");
            String openId = JsonUtils.getJsonStringParam(response, "openid");
            logger.debug("sessionKey is ", sessionKey);
            if (StringUtil.isNotEmpty(openId) && StringUtil.isNotEmpty(sessionKey)) {
                String desKey = DesUtil.encrypt(openId);
                map.put("sessionId", desKey);
                ResponseUtil.getResponse(map, ApiCodeEnum.SUCCESS);
                return ResponseUtil.getResponse(map, ApiCodeEnum.SUCCESS);
            } else {
                return ResponseUtil.getResponse(ApiCodeEnum.SUCCESS.getCode(), JsonUtils.getJsonStringParam(response, "errmsg"));
            }
        } catch (IOException e) {
            return ResponseUtil.getResponse(ApiCodeEnum.RELOGIN);
        }
    }
}
