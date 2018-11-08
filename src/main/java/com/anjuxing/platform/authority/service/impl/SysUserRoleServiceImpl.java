package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.beans.LogType;
import com.anjuxing.platform.authority.common.RequestHolder;
import com.anjuxing.platform.authority.mapper.SysLogMapper;
import com.anjuxing.platform.authority.mapper.SysUserMapper;
import com.anjuxing.platform.authority.mapper.SysUserRoleMapper;
import com.anjuxing.platform.authority.model.SysLogWithBLOBs;
import com.anjuxing.platform.authority.model.SysUser;
import com.anjuxing.platform.authority.model.SysUserRole;
import com.anjuxing.platform.authority.service.SysUserRoleService;
import com.anjuxing.platform.authority.util.JsonMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public int saveUserRoles(int userId, List<SysUserRole> userRoles) {
        return sysUserRoleMapper.saveUserRoles(userId,userRoles);
    }

    @Override
    public List<SysUser> findUsersByRoleId(int roleId){
        List<Integer> userIdList = sysUserRoleMapper.findUserIdsByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.findByIds(userIdList);
    }

    @Override
    public int changeUserRoles(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysUserRoleMapper.findUserIdsByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return 0;
            }
        }
       int result = updateRoleUsers(roleId, userIdList);
        saveRoleUserLog(roleId, originUserIdList, userIdList);
        return result;
    }


    @Transactional
    public int updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysUserRoleMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return 0;
        }
        List<SysUserRole> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysUserRole roleUser = SysUserRole.builder().roleId(roleId).userId(userId).build();
            roleUser.setCreator(RequestHolder.getCurrentUser().getUsername());
            roleUser.setCreateTime(new Date());

            roleUserList.add(roleUser);
        }
        return sysUserRoleMapper.batchSave(roleUserList);
    }
    private void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }
}
