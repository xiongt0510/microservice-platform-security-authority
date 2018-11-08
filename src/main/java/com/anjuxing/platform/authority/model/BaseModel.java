package com.anjuxing.platform.authority.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseModel implements Serializable {



    protected Integer id ;

    protected Integer status;

    protected Date createTime;

    protected String creator;


}
