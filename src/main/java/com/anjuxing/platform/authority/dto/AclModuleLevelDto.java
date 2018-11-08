package com.anjuxing.platform.authority.dto;

import com.anjuxing.platform.authority.model.SysAclModule;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    @Tolerate
    public AclModuleLevelDto(){}

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclDto> aclDtoList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule sysAclModule){
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule,dto);
        return dto;
    }
}
