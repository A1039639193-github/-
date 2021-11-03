package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    /**
     * 注册功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = req.getParameterMap();
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //一次性验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否正确
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请重新输入");
            ObjectMapper mapper = new ObjectMapper();
            //String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            mapper.writeValue(resp.getWriter(), info);
            //resp.getWriter().write(json);
            return;
        }
        //封装
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        //UserService service = new UserServiceImpl();
        Boolean flag = service.findUserByName(user);
        //创建RsultInfo对象
        ResultInfo info = new ResultInfo();
        //判断是否注册成功
        if (flag) {
            //注册成功
            info.setFlag(flag);
        } else {
            //注册失败
            info.setFlag(flag);
            info.setErrorMsg("用户名已存在，注册失败");
        }
        //info转换为json数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //响应数据
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);

    }

    /**
     * 登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取登录数据
        Map<String, String[]> map = req.getParameterMap();
        //创建User对象
        User user = new User();
        //封装对象
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        //UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        if (u != null && "Y".equals(u.getStatus())) {
            //登陆成功
            info.setFlag(true);
            req.getSession().setAttribute("user", u);
        }
        if (u == null) {
            //登陆失败
            info.setFlag(false);
            info.setErrorMsg("密码或用户名错误");
        }
        if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活");
        }
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getWriter(), info);
    }

    /**
     * 激活功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取code
        String code = req.getParameter("code");
        String msg = null;
        if (code != null) {
            //激活验证
            //UserService service = new UserServiceImpl();
            Boolean flag = service.active(code);
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='../login.html'>登录</a>!";
            } else {
                //激活失败
                msg = "激活失败，勿多次提交，请联系管理员!";
            }
        } else {
            //激活失败
            msg = "激活失败，请联系管理员!";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }

    /**
     * 退出功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //移除session 退出登录
        req.getSession().invalidate();
        //跳转登录页面
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }

    /**
     * 查找单个用户的名称
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void findName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        String json = mapper.writeValueAsString(user);
        System.out.println(json);
        resp.getWriter().write(json);
    }


}
