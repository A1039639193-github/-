package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteDao {

    public Favorite findByUidAndRid(int uid,int rid);

    public int findCountByRid(int rid);

    public void add(int uid, int rid);


    public int totalCountQuery(String rname, String lprice, String hprice);

    public List<Route> findUserByCount(int start, int pageSize, String rname, String lprice, String hprice);

     public void addCount(int count ,int rid);
}
