package com.anjuxing.platform.authority.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author xiongt
 * @Description
 */
@Controller
public class PageController {

    @RequestMapping("/sys/aclModule/acl")
    public ModelAndView aclPage() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/sys/dept/page")
    public ModelAndView deptPage() {
        return new ModelAndView("dept");
    }

    @RequestMapping("/sys/log/page")
    public ModelAndView logPage() {
        return new ModelAndView("log");
    }

    @RequestMapping("/sys/role/page")
    public ModelAndView rolePage() {
        return new ModelAndView("role");
    }

    @RequestMapping("/sys/user/noAuth")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }


    @RequestMapping("/signin")
    public ModelAndView signinPage(){
        return new ModelAndView("signin");
    }


    @RequestMapping("/admin/index")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
