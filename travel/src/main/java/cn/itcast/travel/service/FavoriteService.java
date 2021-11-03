package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriteService {

    public Boolean isFavorite(int uid,int rid);

    public void add(int uid, int rid);

    public PageBean<Route> favoriteRank(int currentPage, int pageSize, String rname, String lprice, String hprice);
}
