package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.exception.DataExistException;
import com.anjuxing.platform.authority.mapper.SysDeptMapper;
import com.anjuxing.platform.authority.mapper.SysUserMapper;
import com.anjuxing.platform.authority.model.SysDept;
import com.anjuxing.platform.authority.service.SysDeptService;
import com.anjuxing.platform.authority.service.SysLogService;
import com.anjuxing.platform.authority.util.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public int save(SysDept sysDept) {
        if (checkExist(sysDept.getParentId(),sysDept.getName(),sysDept.getId())){
            throw new DataExistException("部门名重复,请重新填写");
        }
        SysDept dept = SysDept.builder().name(sysDept.getName()).desc(sysDept.getDesc())
                .parentId(sysDept.getParentId()).remark(sysDept.getRemark()).seq(sysDept.getSeq()).build();
        dept.setLevel(LevelUtil.calculateLevel(getLevel(sysDept.getParentId()),sysDept.getParentId()));
        dept.setCreator("system");
        dept.setCreateTime(new Date());
        int result = sysDeptMapper.save(dept);
        sysLogService.saveDeptLog(null,dept);
        return result;
    }

    @Override
    public int update(SysDept dept) {

        if (checkExist(dept.getParentId(),dept.getName(),dept.getId())){
            throw new DataExistException("同一层级下存在相同名称的部门");
        }

        SysDept before = sysDeptMapper.findById(dept.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        if (checkExist(dept.getParentId(),dept.getName(),dept.getId())){
            throw new DataExistException("同一层级下存在相同名称的部门");
        }

        SysDept after = SysDept.builder().desc(dept.getDesc()).name(dept.getName()).parentId(dept.getParentId())
                .remark(dept.getRemark()).seq(dept.getSeq()).build();
        after.setId(dept.getId());
        after.setLevel(LevelUtil.calculateLevel(getLevel(dept.getParentId()),dept.getParentId()));

        int result = updateWithChild(before,after);
        sysLogService.saveDeptLog(before,after);
        return result;
    }

    // 如果要保证事务生效，需要调整这个方法，一个可行的方法是重新创建一个service类，然后把这个方法转移过去
    @Transactional
    public int updateWithChild(SysDept before , SysDept after){
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())){
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysDept> deptList = sysDeptMapper.findChildDeptsByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(deptList)){
                for (SysDept dept : deptList){
                    String level = dept.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel+".") == 0){
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
       return sysDeptMapper.update(after);

    }

    @Override
    public List<SysDept> findDeptByUserId(int userId) {
        return sysDeptMapper.findByUserId(userId);
    }


    /**
     * 得到层级
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.findById(deptId);
        if (Objects.isNull(dept)){
            return null;
        }
        return dept.getLevel();
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    @Override
    public int delete(int id) {

        SysDept dept = sysDeptMapper.findById(id);

        Preconditions.checkNotNull(dept,"待删除的部门不存在，无法删除");

        if (sysDeptMapper.countByParentId(id) > 0){
            throw new DataExistException("当前部门下面有子部门，无法删除");
        }
        //部门下有用户
        if(sysUserMapper.countByDeptId(dept.getId()) > 0) {
            throw new DataExistException("当前部门下面有用户，无法删除");
        }

        return sysDeptMapper.delete(id);
    }
}
