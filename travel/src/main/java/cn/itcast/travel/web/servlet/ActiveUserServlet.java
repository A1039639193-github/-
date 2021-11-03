package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取code
        String code = req.getParameter("code");
        String msg = null;
        if (code!=null){
            //激活验证
            UserService service = new UserServiceImpl();
            Boolean flag =service.active(code);
            if (flag){
                //激活成功
                msg="激活成功，请<a href='login.html'>登录</a>!";
            }else {
                //激活失败
                msg="激活失败，勿多次提交，请联系管理员!";
            }
        }else {
            //激活失败
            msg="激活失败，请联系管理员!";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
