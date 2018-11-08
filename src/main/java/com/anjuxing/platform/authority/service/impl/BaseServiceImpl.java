package com.anjuxing.platform.authority.service.impl;

import com.anjuxing.platform.authority.mapper.BaseMapper;
import com.anjuxing.platform.authority.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
   private BaseMapper<T> mapper;

    @Override
    public int save(T model) {
        return mapper.save(model);
    }

    @Override
    public int cancel(int id) {
        return mapper.cancel(id);
    }

    @Override
    public int delete(int id) {
        return mapper.delete(id);
    }

    @Override
    public int update(T model) {
        return mapper.update(model);
    }

    @Override
    public List<T> findAll() {
        return mapper.findAll();
    }

    @Override
    public T findById(int id) {
        return mapper.findById(id);
    }
}
