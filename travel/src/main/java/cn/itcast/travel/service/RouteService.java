package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * 操作路线业务
 */
public interface RouteService {


    /**
     * 根据uid分页查询路线(包含搜索功能)
     * @param currentPage
     * @param pageSizes
     * @param cid
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int currentPage, int pageSizes, int cid ,String rname);

    public Route findOne(String rid);
}
