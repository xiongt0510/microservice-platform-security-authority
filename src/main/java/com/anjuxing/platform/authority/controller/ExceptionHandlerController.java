package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.exception.AuthorityException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiongt
 * @Description
 * 抛出异常时处理
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData defaultExceptionHandler(HttpServletRequest request, Exception e){

        String url = request.getRequestURI().toString();
        String requestAccept = request.getHeader("accept");
        String contentType = "text/html";
        JsonData jsonData;
        String defaultMsg = "系统错误";

        if (StringUtils.isNotEmpty(requestAccept)){
            if (StringUtils.contains(requestAccept,"application/json") || StringUtils.contains(requestAccept,"text/javascript")
                    || StringUtils.contains(requestAccept,"text/javascript")){
                contentType = "application/json";
            }
        }

        if (contentType.equals("text/html")){
            //页面请求
            log.error("unknown page exception,url:" + url,e);
            jsonData = JsonData.fail(defaultMsg);
        } else if (contentType.equals("application/json")){
            //json 请求
            if (e instanceof AuthorityException){
                jsonData = JsonData.fail(e.getMessage());
            } else {
                log.error("unknown json exception, url:" + url,e);
                jsonData = JsonData.fail(defaultMsg);
            }
        } else {
            //无法判断是页面还是json
            log.error("unknown exception,url:"+url,e);
            jsonData = JsonData.fail(defaultMsg);
        }
        return jsonData;
    }
}
