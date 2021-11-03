package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();

    private RouteImgDao imgDao = new RouteImgImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSizes, int cid,String rname) {
        int totalCount=dao.totalCountQuery(cid,rname);
        int index =(currentPage-1)*5;
        List<Route> list = dao.routeQuery(cid, index, pageSizes,rname);
        int totalPage = 0;
        totalPage=totalCount%pageSizes==0 ? totalCount/pageSizes:(totalCount/pageSizes)+1;
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setList(list);
        pb.setTotalPage(totalPage);
        pb.setPageSizes(pageSizes);
        pb.setTotalCount(totalCount);
        return pb;
    }

    @Override
    public Route findOne(String rid) {
        Route route = dao.findRoute(Integer.parseInt(rid));
//        System.out.println(route.getRouteImgList());
//        System.out.println(route.getSeller());
        List<RouteImg> routeImg = imgDao.findRouteImg(route.getRid());
        route.setRouteImgList(routeImg);
        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
