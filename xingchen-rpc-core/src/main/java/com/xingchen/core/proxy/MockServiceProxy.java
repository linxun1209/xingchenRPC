package com.xingchen.core.proxy;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xing'chen
 * @version 1.0
 * @description: Mock 服务代理（JDK 动态代理）
 * @date 2024/7/17 14:41
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {


    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //根据方法的返回值类型，生成特定的默认值对象
        Class<?> returnType = method.getReturnType();
        log.info("mock invoke{}",method.getName());
        return getDefaultObject(returnType);
    }


    private Object getDefaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class) {
                return false;
            } else if (type == short.class) {
                return (short) 0;
            } else if (type == int.class) {
                return 0;
            } else if (type == long.class) {
                return 0L;
            } else if (type == float.class) {
                return 0.0f;
            } else if (type == double.class) {
                return 0.0;
            } else if (type == char.class) {
                return '\u0000';
            } else if (type == byte.class) {
                return (byte) 0;
            }
        } else {

            //todo 完善 Mock 的逻辑，支持更多返回类型的默认值生成。
            // 参考思路：使用 Faker 之类的伪造数据生成库，来生成默认值。
            // 对象类型
            Faker faker = new Faker();

            // 生成姓名
            String name = faker.name().fullName();
            System.out.println("姓名：" + name);

            // 生成地址
            String address = faker.address().fullAddress();
            System.out.println("地址：" + address);

            // 生成电子邮件
            String email = faker.internet().emailAddress();
            System.out.println("电子邮件：" + email);

            // 生成电话号码
            String phoneNumber = faker.phoneNumber().phoneNumber();
            System.out.println("电话号码：" + phoneNumber);

            // 生成日期
            String date = faker.date().birthday().toString();
            System.out.println("日期：" + date);
        }

        return null;
    }
}

