package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public List<Route> routeQuery(int cid, int index, int pageSizes, String rname) {
        //String sql = "select * from tab_route where cid = ? limit ? , ?";
        StringBuilder sb = new StringBuilder();
        sb.append("select * from tab_route where 1 = 1 ");
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");
        params.add(index);
        params.add(pageSizes);
        String sql = sb.toString();

        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return list;
    }

    @Override
    public int totalCountQuery(int cid, String rname) {
        //String sql = "select count(*) from tab_route where cid=?";
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from tab_route where 1 = 1 ");
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        String sql = sb.toString();
        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());
        return totalCount;
    }

    @Override
    public Route findRoute(int rid) {
        String sql = "select * from tab_route where rid = ? ";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        System.out.println(route);
        return route;
    }


}
