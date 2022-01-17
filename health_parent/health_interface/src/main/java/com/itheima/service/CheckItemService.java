package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

//服务接口

public interface CheckItemService {
    //添加检查项
    public void add(CheckItem checkItem);

    //查询分页数据
    public PageResult findPage(QueryPageBean queryPageBean);

    //根据Id删除检查项
    public void deleteById(Integer id);

    //根据Id修改
    public void updateById(CheckItem checkItem);

    //根据Id修改
    public List<CheckItem> findAll();
}
