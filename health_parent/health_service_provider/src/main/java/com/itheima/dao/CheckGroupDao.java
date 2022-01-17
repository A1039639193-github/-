package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    //添加检查组数据
    public int addCheckGroup(CheckGroup checkGroup);

    public void addCheckItemCheckGroup(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkItemId);

    public Page<CheckGroup> selectByCondition(String queryString);

    public List<Integer> findCheckitemIds(Integer checkGroupId);

    public void deleteGroupRelItem(Integer checkGroupId);

    public void deleteCheckGroup(Integer checkGroupId);

    public int findCountByCheckgroupId(Integer checkGroupId);

    public List<CheckGroup> findAll();

    public void updateCheckgroup(CheckGroup checkGroup);


}
