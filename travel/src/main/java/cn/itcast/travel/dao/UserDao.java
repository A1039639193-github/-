package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    public User findUserByName(String username);

    public void save(User user);

    public void update(User user);

    public User findUserByCode(String code);

    public User findByUsernameAndPassword(String username, String password);
}
