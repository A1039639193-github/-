package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDs());
    @Override
    public List<RouteImg> findRouteImg(int rid) {
        String sql = "select * from tab_route_img where rid = ? ";
        List<RouteImg> routeImgList = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return routeImgList;
    }
}
