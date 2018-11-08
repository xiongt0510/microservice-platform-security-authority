package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.beans.LogType;
import com.anjuxing.platform.authority.common.RequestHolder;
import com.anjuxing.platform.authority.mapper.SysLogMapper;
import com.anjuxing.platform.authority.mapper.SysRoleAclMapper;
import com.anjuxing.platform.authority.model.SysLogWithBLOBs;
import com.anjuxing.platform.authority.model.SysRoleAcl;
import com.anjuxing.platform.authority.service.SysRoleAclService;
import com.anjuxing.platform.authority.util.JsonMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysRoleAclServiceImpl implements SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public int saveRoleAcl(int roleId, List<Integer> aclIds) {

        List<SysRoleAcl> list = new ArrayList<>();
        for (Integer aclId : aclIds){
            list.add(SysRoleAcl.builder()
                    .aclId(aclId)
                    .roleId(roleId).build());
        }

        return sysRoleAclMapper.saveRoleAcl(roleId,list);
    }

    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclIdList = sysRoleAclMapper.findAclIdsByRoleIds(Lists.newArrayList(roleId));
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId, originAclIdList, aclIdList);
    }

    @Transactional
    public void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        sysRoleAclMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for(Integer aclId : aclIdList) {
            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).build();
            roleAcl.setCreator(RequestHolder.getCurrentUser().getUsername());
            roleAcl.setCreateTime(new Date());

            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchSave(roleAclList);
    }

    private void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }
}
