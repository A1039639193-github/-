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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = req.getParameterMap();
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //一次性验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否正确
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请重新输入");
            ObjectMapper mapper = new ObjectMapper();
            //String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            mapper.writeValue(resp.getWriter(),info);
            //resp.getWriter().write(json);
            return;
        }
        //封装
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service
        UserService service = new UserServiceImpl();
        Boolean flag = service.findUserByName(user);
        //创建RsultInfo对象
        ResultInfo info = new ResultInfo();
        //判断是否注册成功
        if (flag){
            //注册成功
            info.setFlag(flag);
        }else {
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
}
