package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.service.SysRoleService;
import com.anjuxing.platform.authority.service.SysTreeService;
import com.anjuxing.platform.authority.service.SysUserService;
import com.anjuxing.platform.authority.util.PathConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping(PathConstant.SYSTEM_USER)
@Slf4j
public class SysUserController extends BaseController<SysUser,SysUserService> {



    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping
    public JsonData getAll(){
        List<SysUser> users = sysUserService.findAll();
        return JsonData.success(users,"成功");
    }

    @GetMapping("/{deptId}/{pageNum}/{pageSize}")
    public JsonData list(@PathVariable int deptId , @PathVariable int pageNum , @PathVariable int pageSize) {

        if (pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> result = sysUserService.findByDeptId(deptId);
        return JsonData.success(new PageInfo<SysUser>(result));
    }

    @GetMapping("/acls/{userId}")
    public JsonData acls(@PathVariable int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.findRolesByUserId(userId));
        return JsonData.success(map);
    }
//
//
//    @PostMapping("/login")
//    public JSONResult<SysUser> login(@RequestParam("username") String username , @RequestParam("password") String password, HttpServletRequest request){
//
//        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
//        SysUser user = userService.findUserByNamePassword(username,md5Password);
//        String message = "登录成功";
//        String resultCode = "success";
//
//        if (Objects.isNull(user)){
//            message = "登录失败";
//            resultCode = "fail";
//        } else {
//            request.getSession().setAttribute("user",user);
//        }
//        JSONResult jsonResult = new JSONResult(user,message);
//        jsonResult.setResultCode(resultCode);
//        return jsonResult;
//    }
//
//    @GetMapping("/search/{pageNum}/{pageSize}")
//    public JSONResult search(@RequestBody SysUser user, @PathVariable int pageNum, @PathVariable(required = false) int pageSize){
//        List<SysUser> users = userService.findUsersByCondition(user);
//        if (pageSize <= 0){
//            pageSize = 10;
//        }
//        PageHelper.startPage(pageNum,pageSize);
//        JSONResult jsonResult = new JSONResult(users);
//        return jsonResult;
//    }
//
//    @PostMapping("/username")
//    public SysUser getUserByUsername(@RequestParam("username") String username){
//
//        return userService.findUserByName(username);
//    }
//
//    @PostMapping("/mobile")
//    public SysUser getUserByMobile(String mobile){
//        return userService.findUserByMobile(mobile);
//    }






}
