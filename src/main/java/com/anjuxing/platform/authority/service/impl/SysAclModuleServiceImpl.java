package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.exception.ParamException;
import com.anjuxing.platform.authority.mapper.SysAclMapper;
import com.anjuxing.platform.authority.mapper.SysAclModuleMapper;
import com.anjuxing.platform.authority.model.SysAclModule;
import com.anjuxing.platform.authority.service.SysAclModuleService;
import com.anjuxing.platform.authority.service.SysLogService;
import com.anjuxing.platform.authority.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * @author xiongt
 * @Description
 */
@Service
public class SysAclModuleServiceImpl extends BaseServiceImpl<SysAclModule> implements SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public int save(SysAclModule sysAclModule) {

        if(checkExist(sysAclModule.getParentId(), sysAclModule.getName(), sysAclModule.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule aclModule = SysAclModule.builder().name(sysAclModule.getName()).parentId(sysAclModule.getParentId()).seq(sysAclModule.getSeq())
                .desc(sysAclModule.getDesc()).remark(sysAclModule.getRemark()).build();
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(sysAclModule.getParentId()), sysAclModule.getParentId()));
        aclModule.setStatus(sysAclModule.getStatus());
        aclModule.setCreateTime(new Date());
        aclModule.setCreator("system");


        int result = sysAclModuleMapper.save(aclModule);
        sysLogService.saveAclModuleLog(null,aclModule);
        return result;
    }

    @Override
    public int update(SysAclModule sysAclModule) {
        if(checkExist(sysAclModule.getParentId(), sysAclModule.getName(), sysAclModule.getId())) {
            throw new ParamException("同一层级下存在相同名称的权限模块");
        }
        SysAclModule before = sysAclModuleMapper.findById(sysAclModule.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule after = SysAclModule.builder().name(sysAclModule.getName()).parentId(sysAclModule.getParentId()).seq(sysAclModule.getSeq())
                .desc(sysAclModule.getDesc()).remark(sysAclModule.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(sysAclModule.getParentId()), sysAclModule.getParentId()));
        after.setId(sysAclModule.getId());
        after.setStatus(sysAclModule.getStatus());


       int result =  updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
        return result;
    }

    @Transactional // 如果要保证事务生效，需要调整这个方法，一个可行的方法是重新创建一个service类，然后把这个方法转移过去
    public int updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysAclModule> aclModuleList = sysAclModuleMapper.findChildAclModuleListByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        return sysAclModuleMapper.update(after);
    }

    public boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.findById(aclModuleId);
        if (aclModule == null) {
            return null;
        }
        return aclModule.getLevel();
    }

    @Override
    public int delete(int id) {

        SysAclModule aclModule = sysAclModuleMapper.findById(id);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有子模块，无法删除");
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有用户，无法删除");
        }
      int result =  sysAclModuleMapper.delete(id);
        return result;
    }
}
