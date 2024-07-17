package com.xingchen.core.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.xingchen.core.serializer.Serializer;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xing'chen
 * @version 1.0
 * @description: SPI加载器（支持键值对映射）
 * @date 2024/7/17 16:05
 *
 *
 * todo 1）实现更多不同协议的序列化器。
 *
 * 参考思路：由于序列化器是单例，要注意序列化器的线程安全性（比如 Kryo 序列化库），可以使用 ThreadLocal。
 *
 * 2）序列化器工厂可以使用懒加载（懒汉式单例）的方式创建序列化器实例。
 *
 * 参考思路：目前是通过 static 静态代码块初始化的。
 *
 * 3）SPI Loader 支持懒加载，获取实例时才加载对应的类。
 *
 * 参考思路：可以使用双检索单例模式。
 */
@Slf4j
public class SpiLoader {

    /**
     * 存储已加载的类  接口名==>（key=>实现类）
     */
    private static Map<String,Map<String,Class<?>>> loaderMap=new ConcurrentHashMap<>();


    /**
     * 对象实例缓存（避免重复的new),类路径==> 对象实例，单例对象
     *
     */
    private static Map<String,Object> instanceCache=new ConcurrentHashMap<>();

    /**
     * 用户自定义SPI目录
     */
    private static final String RPC_CUSTOM_SPI_DIR="MATA_INF/rpc/custom/";

    /**
     * 系统SPI目录
     */
    private static final String RPC_SYSTEM_SPI_DIR="META-INF/rpc/system/";

    /**
     * 动态加载的类列表
     */
    private static final List<Class<?>> LOAD_CLASS_LIST= Arrays.asList(Serializer.class);

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS=new String[]{RPC_SYSTEM_SPI_DIR,RPC_CUSTOM_SPI_DIR};

    /**
     * 加载所有类
     */
    public static void loadAll(){
        log.info("加载所有 SPI");
        for(Class<?> aClass :LOAD_CLASS_LIST){
            load(aClass);
        }
    }


    /**
     * 获取某个接口的实例
     * @param tClass
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T getInstance(Class<?> tClass,String key){
        String tClassName=tClass.getName();
        Map<String,Class<?>> keyClassMap=loaderMap.get(tClassName);
        if(keyClassMap==null){
            throw new RuntimeException(String.format("SpiLoader 未加载%s 类型",tClassName));
        }
        if(!keyClassMap.containsKey(key)){
            throw new RuntimeException(String.format("SPiloader 的 %s 不存在 key=%s 的类型 ",tClass,key));
        }
        //获取到要加载的实现类型
        Class<?> implClass=keyClassMap.get(key);
        //从实例缓存中加载指定类型的实例
        String implClassName=implClass.getName();

        if (!instanceCache.containsKey(implClassName)){
            try {
                instanceCache.put(implClassName,implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                String errorMSg=String.format("%S实例化失败",implClassName);
                throw new RuntimeException(errorMSg,e);
            }

        }
        return (T) instanceCache.get(implClassName);
    }



    public static Map<String,Class<?>> load(Class<?> loadClass){
        log.info("加载类型为{}的SPI",loadClass.getName());
        //扫描路径。用户自定义的SPI级别高于系统的
        Map<String ,Class<?>> keyClassMap=new HashMap<>();
        for (String scanDir:SCAN_DIRS){
            List<URL> resources= ResourceUtil.getResources(scanDir+loadClass.getName());
            for (URL resource : resources){
                try {
                    InputStreamReader inputStreamReader=new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    String line;
                    while ((line =bufferedReader.readLine())!=null){
                        String[] strArray=line.split("=");
                        if(strArray.length>1){
                            String key=strArray[0];
                            String className=strArray[1];
                            keyClassMap.put(key,Class.forName(className));
                        }
                    }
                } catch (Exception e) {
                   log.info("spi resource load error",e);
                }
            }
        }
        loaderMap.put(loadClass.getName(),keyClassMap);
        return keyClassMap;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loadAll();
        System.out.println(loaderMap);
        Serializer serializer = getInstance(Serializer.class, "jdk");
        System.out.println(serializer);
    }
}

