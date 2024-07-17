package com.xingchen.common.model;

import java.io.Serializable;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 用户实体类
 * @date 2024/7/16 19:39
 */
public class User implements Serializable {


    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

