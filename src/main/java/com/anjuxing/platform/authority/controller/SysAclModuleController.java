package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.model.SysAclModule;
import com.anjuxing.platform.authority.service.SysAclModuleService;
import com.anjuxing.platform.authority.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController extends BaseController<SysAclModule,SysAclModuleService> {

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @GetMapping("/tree")
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
