package com.anjuxing.platform.authority.mapper;


import com.anjuxing.platform.authority.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface SysAclModuleMapper extends BaseMapper<SysAclModule> {

    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    List<SysAclModule> findChildAclModuleListByLevel(@Param("level") String level);

    int batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    int countByParentId(@Param("aclModuleId") int aclModuleId);


}
