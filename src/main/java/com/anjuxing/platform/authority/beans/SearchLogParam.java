package com.anjuxing.platform.authority.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@ToString
public class SearchLogParam {

    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime;

    private int pageSize;

    private int pageNum;
}
