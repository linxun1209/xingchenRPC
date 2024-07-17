package com.xingchen.provider.servieImpl;

import com.xingchen.common.model.User;
import com.xingchen.common.service.UserService;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 简易服务者提供实例
 * @date 2024/7/16 19:50
 */
public class EasyProviderXinghhen {
    public static void main(String[] args) {
        //todo 需要获取UserService 的实现对象
        UserService userService=null;
        User user=new User();
        user.setName("xingchen");
        User newUser = userService.getUser(user);
        if(newUser!=null){
            System.out.println(user.getName());
        }else {
            System.out.println("User ==null");
        }
    }
}

