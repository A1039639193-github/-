package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * 检查项持久层
 */
public interface CheckItemDao {

    //添加检查项
    public void add(CheckItem checkItem);

    //查询检查项分页数据
    public Page<CheckItem> selectByCondition(String queryString);

    //根据检查项id查询检查组是否包含检查项
    public long findCountByCheckItemId(Integer id);

    //删除检查项
    public void deleteById(int id);

    //添加检查项
    public void updateById(CheckItem checkItem);

    //查询所有检查项
    public List<CheckItem> findAll();

    //根据检查项id查询
    public CheckItem findById(Integer checkItemId);

}
