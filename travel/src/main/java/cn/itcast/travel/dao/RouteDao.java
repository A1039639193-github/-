package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    public List<Route> routeQuery(int cid, int index, int pageSizes, String rname);

    public int totalCountQuery(int cid, String name);

    public Route findRoute(int rid);


}
