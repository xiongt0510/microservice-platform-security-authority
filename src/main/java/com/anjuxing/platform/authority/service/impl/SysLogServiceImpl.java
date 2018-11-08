package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.beans.LogType;
import com.anjuxing.platform.authority.beans.SearchLogParam;
import com.anjuxing.platform.authority.dto.SearchLogDto;
import com.anjuxing.platform.authority.exception.ParamException;
import com.anjuxing.platform.authority.mapper.*;
import com.anjuxing.platform.authority.model.*;
import com.anjuxing.platform.authority.service.SysLogService;
import com.anjuxing.platform.authority.service.SysRoleAclService;
import com.anjuxing.platform.authority.service.SysUserRoleService;
import com.anjuxing.platform.authority.util.JsonMapper;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
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
public class SysLogServiceImpl extends BaseServiceImpl<SysLogWithBLOBs> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAclMapper sysAclMapper;
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysUserRoleService sysUserRoleService;



    @Override
    public void recover(int id) {
        SysLogWithBLOBs sysLog = sysLogMapper.findById(id);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()){
            case LogType.TYPE_DEPT:
                SysDept beforeDept = sysDeptMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeDept, "待还原的部门已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue())  || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysDept afterDept = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysDept>() {
                });
                afterDept.setCreator("system");
                afterDept.setCreateTime(new Date());
                sysDeptMapper.update(afterDept);
                saveDeptLog(beforeDept, afterDept);
                break;
            case LogType.TYPE_USER:
                SysUser beforeUser = sysUserMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser, "待还原的用户已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue())  || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysUser afterUser = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysUser>() {
                });
                afterUser.setCreator("system");
                afterUser.setCreateTime(new Date());
                sysUserMapper.update(afterUser);
                saveUserLog(beforeUser, afterUser);
                break;
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue())  || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });
                afterAclModule.setCreator("system");
                afterAclModule.setCreateTime(new Date());
                sysAclModuleMapper.update(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的权限点已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue())  || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });
//                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
//                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//                afterAcl.setOperateTime(new Date());
                sysAclMapper.update(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的角色已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue())  || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysRole afterRole = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });
//                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
//                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
//                afterRole.setOperateTime(new Date());
                sysRoleMapper.update(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole, "角色已经不存在了");
                sysRoleAclService.changeRoleAcls(sysLog.getTargetId(), JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.findById(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole, "角色已经不存在了");
                sysUserRoleService.changeUserRoles(sysLog.getTargetId(), JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {
                }));
                break;
            default:;
        }
    }

    @Override
    public List<SysLogWithBLOBs> search(SearchLogParam logParam) {

        SearchLogDto dto = new SearchLogDto();
        dto.setType(logParam.getType());
        if (StringUtils.isNotBlank(logParam.getBeforeSeg())) {
            dto.setBeforeSeg("%" + logParam.getBeforeSeg() + "%");
        }
        if (StringUtils.isNotBlank(logParam.getAfterSeg())) {
            dto.setAfterSeg("%" + logParam.getAfterSeg() + "%");
        }
        if (StringUtils.isNotBlank(logParam.getOperator())) {
            dto.setOperator("%" + logParam.getOperator() + "%");
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(logParam.getFromTime())) {
                dto.setFromTime(dateFormat.parse(logParam.getFromTime()));
            }
            if (StringUtils.isNotBlank(logParam.getToTime())) {
                dto.setToTime(dateFormat.parse(logParam.getToTime()));
            }
        } catch (Exception e) {
            throw new ParamException("传入的日期格式有问题，正确格式为：yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySearchDto(dto);
        List<SysLogWithBLOBs> logList = Lists.newArrayList();
        if (count > 0){
            PageHelper.startPage(logParam.getPageNum(),logParam.getPageSize());
            logList = sysLogMapper.findBySearchDto(dto);
        }
        return logList;
    }

    @Override
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setCreator("system");
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }

    @Override
    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreator("system");
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }

    @Override
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreator("system");
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }

    @Override
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreator("system");
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }

    @Override
    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
//        sysLog.setCreator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setCreator("system");
        sysLog.setCreateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.save(sysLog);
    }
}
