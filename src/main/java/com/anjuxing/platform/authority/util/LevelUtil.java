package com.anjuxing.platform.authority.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @author xiongt
 * @Description
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final  static String ROOT = "0";

    /**
     * 计算层级
     * @param parentLevel
     * @param parentId
     * @return
     * 0
     * 0.1
     * 0.1.2
     * 0.1.3
     * 0.4
     */
    public static String calculateLevel(String parentLevel,int parentId){
        if(StringUtils.isBlank(parentLevel)){
            return ROOT;
        }
        return StringUtils.join(parentLevel,SEPARATOR,parentId);
    }


}
