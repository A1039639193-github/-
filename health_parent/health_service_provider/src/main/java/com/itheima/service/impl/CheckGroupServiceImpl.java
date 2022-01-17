package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.addCheckGroup(checkGroup);
        for (Integer checkItemId : checkItemIds) {
            checkGroupDao.addCheckItemCheckGroup(checkGroup.getId(),checkItemId);
        }
    }

    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> result = page.getResult();
        for(CheckGroup checkGroup : result) {
            List<Integer> checkitemIds = checkGroupDao.findCheckitemIds(checkGroup.getId());
            List<CheckItem> list = new ArrayList<CheckItem>();
            for (Integer checkitemId : checkitemIds) {
                System.out.println(checkitemId);
                CheckItem checkItem = checkItemDao.findById(checkitemId);
                list.add(checkItem);
            }
            checkGroup.setCheckItems(list);
        }
        return new PageResult(total,result);
    }

    public void delete(Integer checkGroupId) {
        int count = checkGroupDao.findCountByCheckgroupId(checkGroupId);
        if (count>0){
            //套餐中包含有该检查组，不允许删除
            throw new RuntimeException("套餐中包含有该检查组，不允许删除");
        }else {
            checkGroupDao.deleteGroupRelItem(checkGroupId);
        checkGroupDao.deleteCheckGroup(checkGroupId);
        }
    }

    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.deleteGroupRelItem(checkGroup.getId());
        checkGroupDao.updateCheckgroup(checkGroup);
        for (Integer checkitemId : checkitemIds) {
            checkGroupDao.addCheckItemCheckGroup(checkGroup.getId(),checkitemId);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        List<CheckGroup> checkGroupList = checkGroupDao.findAll();
        return checkGroupList;
    }

}
