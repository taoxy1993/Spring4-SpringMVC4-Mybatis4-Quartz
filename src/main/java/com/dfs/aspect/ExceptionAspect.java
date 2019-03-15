package com.dfs.aspect;

import com.dfs.exception.TokenException;
import com.dfs.model.ApiCodeEnum;
import com.dfs.utils.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author taoxy 2019/1/3
 */
@ControllerAdvice   // 控制器增强
@ResponseBody
public class ExceptionAspect {

    /**
     * Log4j日志处理
     */
    private static final Logger log = Logger.getLogger(ExceptionAspect.class);

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e);
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "could_not_read_json...");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public String s(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "parameter_validation_exception...");
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("request拦截...request_method_not_supported...", e);
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "request拦截...request_method_not_supported...");
    }


    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public String handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "content_type_not_supported...");
    }

    /**
     * 401 - Token is invaild
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(TokenException.class)
    public String handleTokenException(Exception e) {
        log.error("Token is invaild...", e);
        return ResponseUtil.getResponse("401", "content_type_not_supported...");

    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        log.error("Internal Server Error...", e);
        return ResponseUtil.getResponse(ApiCodeEnum.FAIL.getCode(), "Internal Server Error...");

    }
}
