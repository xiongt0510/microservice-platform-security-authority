package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.dto.DeptLevelDto;
import com.anjuxing.platform.authority.model.SysDept;
import com.anjuxing.platform.authority.service.SysDeptService;
import com.anjuxing.platform.authority.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController extends BaseController<SysDept,SysDeptService> {

    @Autowired
    private SysDeptService deptService;

    @Autowired
    private SysTreeService sysTreeService;



    @GetMapping("/user/{userId}")
    public List<SysDept> getDeptsByUserId(@PathVariable int userId){
        return deptService.findDeptByUserId(userId);
    }


    @GetMapping("/tree")
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

}
