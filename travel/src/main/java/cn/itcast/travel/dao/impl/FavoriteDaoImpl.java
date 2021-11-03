package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDs());

    @Override
    public Favorite findByUidAndRid(int uid, int rid) {
        Favorite favorite = null;
        try {
            String sql = " select * from tab_favorite where uid = ? and rid = ? ";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);
        } catch (Exception e) {
            System.out.println(e);
        }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ? ";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }

    @Override
    public void add(int uid, int rid) {
        String sql = "insert into tab_favorite values( ? , ? , ? )";
        template.update(sql, rid, new Date(), uid);
    }

    @Override
    public int totalCountQuery(String rname, String lprice, String hprice) {
        //String sql = "select count(*) from tab_route ";
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) from tab_route where 1 = 1 ");
        List params = new ArrayList();
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        if (!"  ".equals(lprice) && !"  ".equals(hprice) && lprice != null && lprice.length() > 0 && !"null".equals(lprice) && hprice != null && hprice.length() > 0 && !"null".equals(hprice)) {
            sb.append(" and price between ? and ? ");
            params.add(Integer.parseInt(lprice));
            params.add(Integer.parseInt(hprice));
        }
        String sql = sb.toString();
        Integer totalCount = template.queryForObject(sql, Integer.class, params.toArray());
        return totalCount;
    }

    @Override
    public List<Route> findUserByCount(int start, int pageSize, String rname, String lprice, String hprice) {
        //String sql = " select * from tab_route order by count DESC limit ? , ?  ";
        StringBuilder sb = new StringBuilder();
        sb.append("select * from tab_route where 1 = 1 ");
        List params = new ArrayList();
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        if (lprice != null && lprice.length() > 0 && !"null".equals(lprice) && hprice != null && hprice.length() > 0 && !"null".equals(hprice)) {
            sb.append(" and price between ? and ? ");
            params.add(Integer.parseInt(lprice));
            params.add(Integer.parseInt(hprice));
        }
        sb.append(" order by count DESC limit ? , ? ");
        params.add(start);
        params.add(pageSize);
        String sql = sb.toString();
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return list;
    }

    @Override
    public void addCount(int count, int rid) {
        String sql = "update tab_route set count = ? where rid = ? ";
        template.update(sql, count, rid);
    }
}
