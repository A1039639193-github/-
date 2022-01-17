package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckGroupService {
    //添加检查项
    public void add(CheckGroup checkGroup,Integer[] checkItemIds);

    //查询分页数据
    public PageResult findPage(QueryPageBean queryPageBean);

    //添加检查项
    public void delete(Integer checkGroupId);

    //修改检查组
    public void update(CheckGroup checkGroup,Integer[] checkitemIds);

    //查询所有检查组
    public List<CheckGroup> findAll();
}
