package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.model.SysAcl;
import com.anjuxing.platform.authority.model.SysRole;
import com.anjuxing.platform.authority.service.SysAclService;
import com.anjuxing.platform.authority.service.SysRoleService;
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
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController extends BaseController<SysAcl,SysAclService> {


    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleService sysRoleService;



    @GetMapping("/{actModuleId}/{pageNum}/{pageSize}")
    public JsonData list(@PathVariable int actModuleId , @PathVariable int pageNum , @PathVariable int pageSize){

        if (pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);

        List<SysAcl> sysAcls = sysAclService.findByAclModuleId(actModuleId);

        return JsonData.success(new PageInfo<SysAcl>(sysAcls));
    }

    @GetMapping("/acls/{aclId}")
    public JsonData acls(@PathVariable int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.findRolesByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.findUsersByRoles(roleList));
        return JsonData.success(map);
    }







}
