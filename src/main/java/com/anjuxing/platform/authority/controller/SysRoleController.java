package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.dto.AclModuleLevelDto;
import com.anjuxing.platform.authority.model.SysRole;
import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController extends BaseController<SysRole,SysRoleService> {

    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/user/{userId}")
    public List<SysRole> getRoles(@PathVariable int userId){

        return roleService.findRolesByUserId(userId);
    }




    @PostMapping("/tree/{roleId}")
    public JsonData tree(@PathVariable  int roleId){
        List<AclModuleLevelDto> list = sysTreeService.roleTree(roleId);
        return JsonData.success(list);
    }

    @PostMapping("/changeAcls/{roleId}")
    public JsonData changeAcls(@PathVariable int roleId, @RequestBody List<Integer> aclIdList) {
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @PostMapping("/changeUsers/{roleId}")
    public JsonData changeUsers(@PathVariable int roleId, @RequestBody List<Integer> userIdList) {
//        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysUserRoleService.changeUserRoles(roleId, userIdList);
        return JsonData.success();
    }

    @PostMapping("/users/{roleId}")
    public JsonData users(@PathVariable  int roleId) {
        List<SysUser> selectedUserList = sysUserRoleService.findUsersByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.findAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for(SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonData.success(map);
    }







}
