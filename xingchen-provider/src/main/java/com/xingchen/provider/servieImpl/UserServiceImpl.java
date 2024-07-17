package com.xingchen.provider.servieImpl;
import com.xingchen.common.*;
import com.xingchen.common.model.User;
import com.xingchen.common.service.UserService;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 用户实现类
 * @date 2024/7/16 19:45
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户的名字："+user.getName());
        return user;
    }
}

