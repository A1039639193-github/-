package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();

    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String currentPageStr = request.getParameter("currentPage");
        String pageSizesStr = request.getParameter("pageSizes");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSizes = 0;
        if (pageSizesStr != null && pageSizesStr.length() > 0) {
            pageSizes = Integer.parseInt(pageSizesStr);
        } else {
            pageSizes = 5;
        }
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        //调用service方法
        PageBean<Route> pb = service.pageQuery(currentPage, pageSizes, cid, rname);
        //序列化对象
        writeValues(pb, response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String rid = request.getParameter("rid");
        //调用service方法
        Route route =service.findOne(rid);
        //回写
        writeValues(route,response);


    }
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String _rid = request.getParameter("rid");
        int rid;
        if (_rid != null && _rid.length() > 0) {
            rid = Integer.parseInt(_rid);
        }else {
            rid =0;
        }
        //获取uid
        int uid;
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            uid = 0 ;
        }else {
            uid = user.getUid();
        }
        //调用FavoriteService
        System.out.println(uid+","+rid);
        Boolean flag = favoriteService.isFavorite(uid, rid);
        System.out.println("flag:"+flag);
        //写数据到客户端
        writeValues(flag,response);

    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _rid = request.getParameter("rid");
        int rid = Integer.parseInt(_rid);
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        boolean flag;
        if (user==null){
            flag=false;
            writeValues(flag,response);
            return;
        }else {
            flag=true;
            writeValues(flag,response);
            uid = user.getUid();
        }
        //调用favoriteService
        favoriteService.add(uid,rid);
    }



}
