package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/remote")
public class RemoteController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/user/username")
    public SysUser getUserByUsername(@RequestParam("username") String username){
        SysUser sysUser = sysUserService.findUserByName(username);
        return sysUser;
    }


}
