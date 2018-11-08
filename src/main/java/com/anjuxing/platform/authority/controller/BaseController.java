package com.anjuxing.platform.authority.controller;


import com.anjuxing.platform.authority.common.JsonData;
import com.anjuxing.platform.authority.common.ValidateResult;
import com.anjuxing.platform.authority.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.anjuxing.platform.authority.util.PathConstant.*;


/**
 * @author xiongt
 * @Description
 */

public class BaseController<T,S extends BaseService<T>> {



    @Autowired
    private S service;
    /**
     * 保存
     * @param t
     * @return
     */
    @PostMapping
    public JsonData save(@Valid @RequestBody T t, BindingResult errors){

        JsonData jsonData ;
        List<ValidateResult> validateResults = validateResults(errors);
        if (validateResults.size() >0){
            jsonData = JsonData.fail(validateResults,"保存失败");
        } else {
            int result = service.save(t);
            jsonData = JsonData.success(t,"保存成功");
        }

        return jsonData;
    }

    protected List<ValidateResult> validateResults(BindingResult errors){
        List<ValidateResult> validateResults = new ArrayList<>();
        if (errors.hasErrors()){
            errors.getAllErrors().stream().forEach(error -> {
                ValidateResult validateResult = new ValidateResult(
                        error.getCode(),
                        error.getDefaultMessage());

                validateResults.add(validateResult);
            });
        }
        return validateResults;
    }

    @DeleteMapping(PATH_ID)
    public JsonData delete(@PathVariable int id){

        int result = service.delete(id);

        return JsonData.success(id,"删除成功");
    }

    @PutMapping(PATH_CANCEL)
    public JsonData cancel(@PathVariable  int id){

        int result = service.cancel(id);

        return JsonData.success(id,"删除成功");
    }

    @PutMapping
    public JsonData update(@Valid @RequestBody T t, BindingResult errors){

        JsonData jsonData ;
        List<ValidateResult> validateResults = validateResults(errors);
        if (validateResults.size() >0){
            jsonData = JsonData.fail(validateResults,"更新失败");
        } else {
            int result = service.update(t);
            jsonData = JsonData.success(t,"更新成功");
        }

        return jsonData;
    }

    @GetMapping(PATH_PAGE)
    public JsonData findAll(@PathVariable int pageNum , @PathVariable( required = false) int pageSize){
        if (pageSize <= 0){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum,pageSize);

        List<T> list = service.findAll();

        return JsonData.success(new PageInfo<T>(list));
    }

    @GetMapping("/list")
    public JsonData list(){

        List<T> list = service.findAll();

        return JsonData.success(list);
    }

    @GetMapping(PATH_ID)
    public JsonData findById(@PathVariable int id){

        T t = service.findById(id);

        return JsonData.success(t);
    }

    protected boolean checkExist(){
        return  false;
    }





}
