package com.dfs.controller.app;

import com.dfs.annotation.IgnoreSecurity;
import com.dfs.entity.SenseAgroSpecialist;
import com.dfs.entity.SenseAgroUser;
import com.dfs.model.ApiCodeEnum;
import com.dfs.service.SenseAgroSpecialistService;
import com.dfs.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author taoxy 2019/2/1
 */
@RestController
@RequestMapping("/specialist")
public class AppLoginController {
    @Autowired
    private SenseAgroSpecialistService senseAgroSpecialistService;
    private static final Logger log = Logger.getLogger(AppLoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @IgnoreSecurity
    public String appLogin(@RequestBody String param, HttpServletResponse resp, HttpServletRequest req) {
        String name = JsonUtils.getJsonStringParam(param, "name");
        String passwd = JsonUtils.getJsonStringParam(param, "passwd");
        Map<String, Object> map = new HashMap<>();
        log.info("查询开始");
        try {
            SenseAgroSpecialist senseAgroSpecialist = senseAgroSpecialistService.login(name, Md5.GetMD5Code(passwd));
            if (senseAgroSpecialist != null) {
                int setSpecialistId = senseAgroSpecialist.getId();//专家id
                int nowTime = (int) (new Date().getTime() / 1000);//时间以秒存储
                int valid = nowTime + 2 * 24 * 60 * 60;//超时时间2天
                //String token = TokenManager.createToken(senseAgroSpecialist.getId(),flag,minute);
                String token = DesUtil.encrypt(setSpecialistId + "-" + nowTime);
                log.debug("**** Generate app Token **** : " + token);
                SenseAgroUser senseAgroUser = new SenseAgroUser();
                senseAgroUser.setSpecialistId(setSpecialistId);
                senseAgroUser.setValidTime(valid);
                senseAgroUser.setAddTime(nowTime);
                senseAgroUser.setSessionid(token);
                senseAgroSpecialistService.addSenseAgroUser(senseAgroUser);
                resp.setHeader("Token", token);
                map.put("specialistId", setSpecialistId);
                return ResponseUtil.getResponse(map, ApiCodeEnum.SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "帐号或密码错误");
    }
}
