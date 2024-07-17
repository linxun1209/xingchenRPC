package com.xingchen.consumer;

import com.xingche.core.proxy.ServiceProxyFactory;
import com.xingchen.common.model.User;
import com.xingchen.common.service.UserService;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 简易消费者实例
 * @date 2024/7/16 19:55
 */
public class EasyConsumerXingchen {
    public static void main(String[] args) {
        //静态代理
        // UserService userService=new UserServiceProxy();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("xingchen");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}

