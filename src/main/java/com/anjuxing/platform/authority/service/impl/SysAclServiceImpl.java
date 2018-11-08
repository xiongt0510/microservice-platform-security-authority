package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.exception.ParamException;
import com.anjuxing.platform.authority.mapper.SysAclMapper;
import com.anjuxing.platform.authority.model.SysAcl;
import com.anjuxing.platform.authority.service.SysAclService;
import com.anjuxing.platform.authority.service.SysLogService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysAclServiceImpl extends BaseServiceImpl<SysAcl> implements SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public List<SysAcl> findAclsByRoleId(int roleId) {
        return sysAclMapper.findByRoleId(roleId);
    }

    @Override
    public int save(SysAcl sysAcl) {

        if (checkExist(sysAcl.getAclModuleId(), sysAcl.getName(), sysAcl.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder().name(sysAcl.getName()).aclModuleId(sysAcl.getAclModuleId()).url(sysAcl.getUrl())
                .type(sysAcl.getType()).seq(sysAcl.getSeq()).remark(sysAcl.getRemark()).build();
        acl.setCode(generateCode());
        acl.setStatus(sysAcl.getStatus());
        acl.setCreator("system");
        acl.setCreateTime(new Date());

        int result = sysAclMapper.save(acl);

        sysLogService.saveAclLog(null,acl);

        return result;
    }

    @Override
    public int update(SysAcl sysAcl) {

        if (checkExist(sysAcl.getAclModuleId(), sysAcl.getName(), sysAcl.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.findById(sysAcl.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder().name(sysAcl.getName()).aclModuleId(sysAcl.getAclModuleId()).url(sysAcl.getUrl())
                .type(sysAcl.getType()).seq(sysAcl.getSeq()).remark(sysAcl.getRemark()).build();
        after.setId(sysAcl.getId());
        after.setStatus(sysAcl.getStatus());
        after.setCreator("system");
        after.setCreateTime(new Date());

       int result  = sysAclMapper.update(after);
        sysLogService.saveAclLog(before, after);
        return result;
    }

    @Override
    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    @Override
    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }

    @Override
    public List<SysAcl> findByAclModuleId(int aclModuleId) {
        List<SysAcl> aclList = Lists.newArrayList();
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            aclList = sysAclMapper.findByAclModuleId(aclModuleId);
        }
        return aclList;
    }




}
