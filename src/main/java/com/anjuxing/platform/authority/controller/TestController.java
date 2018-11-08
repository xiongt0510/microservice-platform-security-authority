package com.anjuxing.platform.authority.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xiongt
 * @Description
 */
@Controller
public class TestController   {

    @GetMapping("/index")
    public String test(){
        return "/dept";
    }


    @GetMapping("/test")
    public String test2(){
        return "/test";
    }


}
