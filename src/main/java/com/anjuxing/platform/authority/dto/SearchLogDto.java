package com.anjuxing.platform.authority.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author xiongt
 * @Description
 */
@Getter
@Setter
@ToString
public class SearchLogDto {

    private Integer type;
    private String beforeSeg;
    private String afterSeg;
    private String operator;
    private Date fromTime; //yyyy-MM-dd HH:mm:ss
    private Date toTime; //yyyy-MM-dd HH:mm:ss

}
