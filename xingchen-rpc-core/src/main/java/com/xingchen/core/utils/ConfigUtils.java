package com.xingchen.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author xing'chen
 * @version 1.0
 * @description: 配置工具类
 * @date 2024/7/17 10:41
 */
public class ConfigUtils {


    /**
     * 加载配置对象
     * @param tClass
     * @param prefix
     * @return
     * @param <T>
     */
    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass, prefix, "");
    }


    /**
     * 加载配置对象 支持区分环境
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */


    /**
     * TODO: 2024/7/17  提供以下扩展思路，可自行实现：
     *
     * 1）支持读取 application.properties、application.yaml 等不同格式的配置文件。
     *
     * 2）支持监听配置文件的变更，并自动更新配置对象。1609216259264610306_0.14402598629452412
     *
     * 参考思路：使用 Hutool 工具类的 props.autoLoad() 可以实现配置文件变更的监听和自动加载。
     *
     * 3）配置文件支持中文。
     *
     * 参考思路：需要注意编码问题1609216259264610306_0.5205157658497359
     *
     * 4）配置分组。后续随着配置项的增多，可以考虑对配置项进行分组。
     *
     * 参考思路：可以通过嵌套配置类实现。
     * @param tClass
     * @param prefix
     * @param environment
     * @return
     * @param <T>
     */


    public static <T> T loadConfig(Class<T> tClass,String prefix,String environment){
        StringBuilder configFileBuilder=new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props=new Props(configFileBuilder.toString());
        return props.toBean(tClass,prefix);
    }
}

