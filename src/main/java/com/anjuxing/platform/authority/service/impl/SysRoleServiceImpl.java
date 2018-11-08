package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.exception.ParamException;
import com.anjuxing.platform.authority.mapper.SysRoleAclMapper;
import com.anjuxing.platform.authority.mapper.SysRoleMapper;
import com.anjuxing.platform.authority.mapper.SysUserMapper;
import com.anjuxing.platform.authority.mapper.SysUserRoleMapper;
import com.anjuxing.platform.authority.model.SysRole;
import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.service.SysLogService;
import com.anjuxing.platform.authority.service.SysRoleService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;



    @Override
    public List<SysRole> findRolesByUserId(int userId) {
        return sysRoleMapper.findByUserId(userId);
    }

    @Override
    public List<SysRole> findRolesByAclId(int aclId) {

        List<Integer> roleIdList = sysRoleAclMapper.findRoleIdsByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.findByIdList(roleIdList);
    }

    @Override
    public List<SysUser> findUsersByRoles(List<SysRole> roles){
        if (CollectionUtils.isEmpty(roles)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roles.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysUserRoleMapper.findUserIdsByRoleIds(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.findByIds(userIdList);
    }

    @Override
    public int save(SysRole sysRole) {



        if (checkExist(sysRole.getName(), sysRole.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole role = SysRole.builder().name(sysRole.getName()).type(sysRole.getType())
                .remark(sysRole.getRemark()).desc(sysRole.getDesc()).build();

        role.setCreateTime(new Date());
        role.setCreator("system");
        role.setStatus(1);

        int result = sysRoleMapper.save(role);
        sysLogService.saveRoleLog(null, role);
        return result;
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }

    @Override
    public int update(SysRole sysRole) {

        if (checkExist(sysRole.getName(), sysRole.getId())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.findById(sysRole.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = SysRole.builder().name(sysRole.getName()).type(sysRole.getType())
                .remark(sysRole.getRemark()).build();
        after.setStatus(sysRole.getStatus());
        after.setId(sysRole.getId());

        int result = sysRoleMapper.update(after);
        sysLogService.saveRoleLog(before, after);
        return result;
    }

//    @Override
//    public List<SysRole> findRolesByUserId(int userId) {
//        List<Integer> roleIdList = .getRoleIdListByUserId(userId);
//        if (CollectionUtils.isEmpty(roleIdList)) {
//            return Lists.newArrayList();
//        }
//        return sysRoleMapper.getByIdList(roleIdList);
//    }
}
