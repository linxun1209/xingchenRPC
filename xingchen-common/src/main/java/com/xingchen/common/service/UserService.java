package com.xingchen.common.service;

import com.xingchen.common.model.User;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 用户服务类
 * @date 2024/7/16 19:41
 */
public interface UserService {


    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);


    /**
     * 新方法 - 获取数字
     */
    default  short getNumber() {
        return 1;
    }
}

