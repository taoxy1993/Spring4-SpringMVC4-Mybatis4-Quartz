package com.dfs.controller.boss;

import com.dfs.annotation.IgnoreSecurity;
import com.dfs.authorization.TokenManager;
import com.dfs.entity.SenseAgroAdmin;
import com.dfs.model.ApiCodeEnum;
import com.dfs.service.SenseAgroAdminService;
import com.dfs.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author taoxy 2019/1/3
 */
@RestController
@RequestMapping("/boss")
public class BossLoginController {
    @Autowired
    private SenseAgroAdminService userService;
    private static final Logger log = Logger.getLogger(BossLoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @IgnoreSecurity
    public String login(@RequestBody String param, HttpServletResponse response, HttpServletRequest request) {
        String name = JsonUtils.getJsonStringParam(param, "name");
        String passwd = JsonUtils.getJsonStringParam(param, "passwd");

        log.info("查询开始");
        SenseAgroAdmin senseAgroAdmin = userService.login(name, Md5.GetMD5Code(passwd));
        if (senseAgroAdmin != null) {
            int minute = 120;//超时时间,分钟
            String token = TokenManager.createToken(senseAgroAdmin.getId(), minute);
            log.debug("**** Generate Token **** : " + token);
            response.setHeader("Token", token);
            HttpSession session = request.getSession(true);
            //为了读取当前用户
            session.setAttribute("userId", senseAgroAdmin.getId());
            return ResponseUtil.getResponse(ApiCodeEnum.SUCCESS);
        }
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "帐号或者密码错误");
    }
}
