package com.anjuxing.platform.authority.service;


import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface BaseService<T>  {

    int save(T model);

    int cancel(int id);

    int delete(int id);

    int update(T model);

    List<T> findAll();

    T findById(int id);
}
