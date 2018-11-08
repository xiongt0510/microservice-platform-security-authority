package com.anjuxing.platform.authority.common;


import com.anjuxing.platform.authority.model.SysUser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public static boolean isJsonRequest(HttpServletRequest request, HttpServletResponse response){
        String url = request.getRequestURI().toString();
        String requestAccept = request.getHeader("accept");
        String contentType = "text/html";

        boolean isJsonRequest = false;

        if (StringUtils.isNotEmpty(requestAccept)){
            if (StringUtils.contains(requestAccept,"application/json") || StringUtils.contains(requestAccept,"text/javascript")
                    || StringUtils.contains(requestAccept,"text/javascript")){
                contentType = "application/json";
            }
        }
        if (contentType.equals("application/json")){
            isJsonRequest = true;
        }
        return  isJsonRequest;
    }


}
