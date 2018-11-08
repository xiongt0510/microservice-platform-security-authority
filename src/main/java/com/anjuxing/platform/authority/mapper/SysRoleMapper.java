package com.anjuxing.platform.authority.mapper;



import com.anjuxing.platform.authority.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xiongt
 * @Description
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findByUserId(@Param("userId") Integer userId);

    int countByName(@Param("name") String name, @Param("id") Integer id);

    List<SysRole> findByIdList(@Param("idList") List<Integer> idList);




}
