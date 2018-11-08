package com.anjuxing.platform.authority.mapper;


import com.anjuxing.platform.authority.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 根据用户id 查询部门
     * @param userId
     * @return
     */
    List<SysDept> findByUserId(int userId);


    List<SysDept> findChildDeptsByLevel(@Param("level") String level);


    int batchUpdateLevel(@Param("sysDeptList") List<SysDept> sysDeptList);


    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    int countByParentId(@Param("deptId") int deptId);


}
