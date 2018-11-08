package com.anjuxing.platform.authority.mapper;


import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public interface BaseMapper<T> {

    /**
     * 新增
     * @param t
     * @return
     */
     int save(T t);

    /**
     * 批量新增
     * @param list
     * @return
     */
     int batchSave(List<T> list);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int cancel(Integer id);

    /**
     * 批量逻辑删除
     * @param ids
     * @return
     */
    int batchCancel(List<Integer> ids);

    /**
     * 物理删除
     * @param id
     * @return
     */
    int delete(Integer id);

    /**
     * 批量物理删除
     * @param ids
     * @return
     */
    int batchDelete(List<Integer> ids);


    /**
     * 更新
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 批量更新
     * @param list
     * @return
     */
    int batchUpdate(List<T> list);


    /**
     * 批量更新
     * @param ids
     * @return
     */
    int batchUpdata(List<Integer> ids);



    /**
     * 查找所有
     * @return
     */
    List<T> findAll();

    /**
     * 查看单个
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * 检查是否存在
     * @param id
     * @return
     */
    Integer checkExist(Integer id);




}
