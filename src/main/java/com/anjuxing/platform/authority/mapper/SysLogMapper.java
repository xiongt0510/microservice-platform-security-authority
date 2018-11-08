package com.anjuxing.platform.authority.mapper;

import com.anjuxing.platform.authority.dto.SearchLogDto;
import com.anjuxing.platform.authority.model.SysLog;
import com.anjuxing.platform.authority.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xiongt
 * @Description
 */
public interface SysLogMapper extends BaseMapper<SysLogWithBLOBs> {

    int update(SysLog sysLog);

    int updateSelective(SysLogWithBLOBs sysLogWithBLOBs);

    int updateSysLog(SysLog sysLog);

    int countBySearchDto(@Param("dto") SearchLogDto dto);

    List<SysLogWithBLOBs> findBySearchDto(@Param("dto") SearchLogDto dto);



}
