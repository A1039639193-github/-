package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet{
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 收藏排行榜(分页查询)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favoriteRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        String lprice = request.getParameter("lprice");
        String hprice = request.getParameter("hprice");
        int currentPage;
        int pageSize;
        //lprice = lprice.replace(" ","");
        //hprice = hprice.replace(" ","");
        if ("".equals(lprice)){
            lprice=null;
        }
        if ("".equals(hprice)){
            hprice=null;
        }
        if (_currentPage!=null && _currentPage.length()>0 ){
            currentPage = Integer.parseInt(_currentPage);
        }else {
            currentPage=1;
        }
        if (_pageSize!=null&&_pageSize.length()>0){
            pageSize=Integer.parseInt(_pageSize);
        }else {
            pageSize=8;
        }
        //调用FavoriteService
        PageBean<Route> pb =favoriteService.favoriteRank(currentPage,pageSize,rname,lprice,hprice);
        //写回数据
        writeValues(pb,response);
    }
}
