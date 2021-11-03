package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public Boolean findUserByName(User u) {
        User user = dao.findUserByName(u.getUsername());
        if (user!=null){
            //注册失败
            System.out.println("用户名已存在，注册失败");
            return false;
        }
        String code =UuidUtil.getUuid();
        u.setStatus("N");
        u.setCode(code);
        dao.save(u);
        MailUtils.sendMail(u.getEmail(),"<a href='http://tom.vaiwan.com/travel/user/active?code="+u.getCode()+"'>黑马旅游网</a>","激活邮件");
        return true;
    }

    @Override
    public Boolean active(String code) {
        User user =dao.findUserByCode(code);
        if (user!=null){
            //激活成功
            dao.update(user);
            return true;
        }else {
            //激活失败
            return false;
        }

    }

    @Override
    public User login(User user) {
        User u =dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());

        return u;
    }
}
