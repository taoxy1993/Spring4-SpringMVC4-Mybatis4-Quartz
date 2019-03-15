package com.dfs.aspect;

import com.dfs.annotation.*;
import com.dfs.authorization.TokenManager;
import com.dfs.exception.TokenException;
import com.dfs.entity.SenseAgroMember;
import com.dfs.entity.SenseAgroUser;
import com.dfs.model.ApiCodeEnum;
import com.dfs.service.SenseAgroAppService;
import com.dfs.service.SenseAgroMemberService;
import com.dfs.utils.*;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;


/**
 * @author taoxy 2019/1/3
 */
@Component
@Aspect
public class SecurityAspect {
    /**
     * Log4j日志处理
     */
    private static final Logger log = Logger.getLogger(SecurityAspect.class);

    @Autowired
    private SenseAgroMemberService senseAgroMemberService;
    @Autowired
    private SenseAgroAppService senseAgroAppService;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        log.debug("methodSignature : " + methodSignature);
        Method method = methodSignature.getMethod();
        log.debug("Method : " + method.getName() + " : " + method.isAnnotationPresent(IgnoreSecurity.class));
        // 若目标方法忽略了安全性检查,则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }
        //小程序端和APP端登录验证
        if (method.isAnnotationPresent(AppAndApplet.class)) {
            HttpServletRequest req = WebContextUtil.getRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String param = RequestBodyUtils.read(reader);
            String sessionId;
            log.info("param is " + param);
            if (StringUtil.isEmpty(param)) {
                sessionId = req.getParameter("sessionId");
            } else {
                sessionId = JsonUtils.getJsonStringParam(param, "sessionId");
            }
            String openId;
            try {
                openId = DesUtil.decrypt(sessionId);
            } catch (Exception e) {
                openId = "";
            }
            log.info("openid is " + openId);
            SenseAgroMember senseAgroMember = senseAgroMemberService.findMember(openId);
            log.info("select  SenseAgroMember success");
            List<SenseAgroUser> listUserSession = senseAgroAppService.findAppUserByToken((int) (new Date().getTime() / 1000), sessionId);
            if (senseAgroMember == null && listUserSession.size() == 0) {
                return ResponseUtil.getResponse(ApiCodeEnum.RELOGIN);
            }
            return pjp.proceed();
        }
        //小程序登录验证
        if (method.isAnnotationPresent(AppletSecurity.class)) {
            HttpServletRequest req = WebContextUtil.getRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String param = RequestBodyUtils.read(reader);
            String sessionId;
            if (StringUtil.isEmpty(param)) {
                sessionId = req.getParameter("sessionId");
            } else {
                sessionId = JsonUtils.getJsonStringParam(param, "sessionId");
            }
            String openId;
            try {
                openId = DesUtil.decrypt(sessionId);
            } catch (Exception e) {
                openId = "";
            }
            log.info("openid is " + openId);
            SenseAgroMember senseAgroMember = senseAgroMemberService.findMember(openId);
            log.info("select  SenseAgroMember success");
            if (senseAgroMember == null) {
                return ResponseUtil.getResponse(ApiCodeEnum.RELOGIN);
            }
            return pjp.proceed();
        }
        //Boss后台登录验证
        if (method.isAnnotationPresent(BossSecurity.class)) {
            String token = WebContextUtil.getRequest().getHeader("Token");
            // redis检查 token 有效性
            if (!TokenManager.checkToken(token)) {
                String message = String.format("token [%s] is invalid", token);
                log.debug("message : " + message);
                throw new TokenException(message);
            }
            return pjp.proceed();
        }
        //App端登录验证
        if (method.isAnnotationPresent(AppSecurity.class)) {
            HttpServletRequest req = WebContextUtil.getRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String param = RequestBodyUtils.read(reader);
            String sessionId;
            if (StringUtil.isEmpty(param)) {
                sessionId = req.getParameter("sessionId");
            } else {
                sessionId = JsonUtils.getJsonStringParam(param, "sessionId");
            }
            List<SenseAgroUser> listUserSession = senseAgroAppService.findAppUserByToken((int) (new Date().getTime() / 1000), sessionId);
            if (listUserSession.size() == 0) {//size大小为当前有效登录次数
                throw new TokenException("认证已过期");
            }
            return pjp.proceed();
        }
        //App端和BOSS端登录验证
        if (method.isAnnotationPresent(AppAndBoss.class)) {
            HttpServletRequest req = WebContextUtil.getRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String param = RequestBodyUtils.read(reader);
            String sessionId;
            if (StringUtil.isEmpty(param)) {
                sessionId = req.getParameter("sessionId");
            } else {
                sessionId = JsonUtils.getJsonStringParam(param, "sessionId");
            }
            List<SenseAgroUser> listUserSession = senseAgroAppService.findAppUserByToken((int) (new Date().getTime() / 1000), sessionId);
            String token = req.getHeader("Token");
            if (!TokenManager.checkToken(token) && listUserSession.size() == 0) {
                throw new TokenException("认证已过期");
            }
            return pjp.proceed();
        }
        //三端登录验证
        if (method.isAnnotationPresent(AppAppletBoss.class)) {
            HttpServletRequest req = WebContextUtil.getRequest();
            BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8"));
            String param = RequestBodyUtils.read(reader);
            String sessionId;
            if (StringUtil.isEmpty(param)) {
                sessionId = req.getParameter("sessionId");
            } else {
                sessionId = JsonUtils.getJsonStringParam(param, "sessionId");
            }
            List<SenseAgroUser> listUserSession = senseAgroAppService.findAppUserByToken((int) (new Date().getTime() / 1000), sessionId);
            String token = req.getHeader("Token");
            String openId;
            try {
                openId = DesUtil.decrypt(sessionId);
            } catch (Exception e) {
                openId = "";
            }
            log.info("openid is " + openId);
            SenseAgroMember senseAgroMember = senseAgroMemberService.findMember(openId);
            if (!TokenManager.checkToken(token) && listUserSession.size() == 0 && senseAgroMember == null) {
                return ResponseUtil.getResponse(ApiCodeEnum.RELOGIN);
            }
            return pjp.proceed();
        }
        throw new TokenException("类型不支持");
    }
}
