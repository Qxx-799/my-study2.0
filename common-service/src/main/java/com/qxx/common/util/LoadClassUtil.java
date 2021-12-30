package com.qxx.common.util;

import com.qxx.common.service.ClassLoaderService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoadClassUtil {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
//        ClassLoaderService classLoader = new ClassLoaderService("/Users/xiaoxuanqi/Downloads/my-study2.0/common-service/target/classes");
//        Class<?> aClass = classLoader.loadClass("com.qxx.common.entity.User");
//        Object o = aClass.newInstance();
//        Method method = aClass.getDeclaredMethod("sout", null);
//        method.invoke(o,null);
//
//        System.out.println(aClass.getClassLoader().getClass().getName());

        LoadClassUtil util = new LoadClassUtil();
        while (true){
            util.compute();
        }
    }

    public int compute(){
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }
}
