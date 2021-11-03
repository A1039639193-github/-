package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginUserServlet")
public class LoginUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        ResultInfo info = new ResultInfo();
        if (u!=null&&"Y".equals(u.getStatus())) {
            //登陆成功
            info.setFlag(true);
            req.getSession().setAttribute("user",u);
        }
        if (u==null) {
            //登陆失败
            info.setFlag(false);
            info.setErrorMsg("密码或用户名错误");
        }
        if (u!=null&&!"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("用户尚未激活");
        }
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getWriter(), info);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
