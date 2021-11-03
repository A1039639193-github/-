package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao dao = new FavoriteDaoImpl();

    private RouteDao routeDao = new RouteDaoImpl();
    @Override
    public Boolean isFavorite(int uid, int rid) {
        Favorite favorite = dao.findByUidAndRid(uid, rid);

        return favorite !=null;
    }

    @Override
    public void add(int uid, int rid) {

        dao.add(uid,rid);
        Route route = routeDao.findRoute(rid);
        int count =route.getCount()+1;
        dao.addCount(count,rid);
    }

    @Override
    public PageBean<Route> favoriteRank(int currentPage, int pageSize, String rname, String lprice, String hprice) {
        int totalCount = dao.totalCountQuery(rname,lprice,hprice);
        int start =(currentPage-1)*8;
        List<Route> list =dao.findUserByCount(start,pageSize,rname,lprice,hprice);
        PageBean<Route> pb = new PageBean<Route>();
        int totaPage;
        totaPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalCount(totalCount);
        pb.setList(list);
        pb.setTotalPage(totaPage);
        pb.setCurrentPage(currentPage);
        pb.setPageSizes(pageSize);
        return pb;
    }
}
