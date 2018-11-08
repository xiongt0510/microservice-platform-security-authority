package com.anjuxing.platform.authority.controller;

import com.anjuxing.platform.authority.beans.SearchLogParam;
import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.model.SysLogWithBLOBs;
import com.anjuxing.platform.authority.service.SysLogService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@RequestMapping("/sys/log")
@RestController
@Slf4j
public class SysLogController extends BaseController<SysLogWithBLOBs,SysLogService> {
    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/recover/{id}")
    public JsonData recover(@PathVariable int id) {
        sysLogService.recover(id);
        return JsonData.success();
    }

    @GetMapping("/search/{pageNum}/{pageSize}")
    public JsonData searchPage(SearchLogParam param, @PathVariable int pageNum , @PathVariable int pageSize) {

        if (pageSize <= 0){
            pageSize = 10;
        }


        param.setPageNum(pageNum);
        param.setPageSize(pageSize);

        List<SysLogWithBLOBs> logs = sysLogService.search(param);

        return JsonData.success(new PageInfo<SysLogWithBLOBs>(logs));
    }
}
