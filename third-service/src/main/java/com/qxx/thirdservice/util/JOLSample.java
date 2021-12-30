package com.qxx.thirdservice.util;

import com.qxx.thirdservice.service.ServerSocketService;
import org.openjdk.jol.info.ClassLayout;

public class JOLSample {

    public static void main(String[] args) {
        ClassLayout classLayout = ClassLayout.parseInstance(new Object());
        System.out.println(classLayout.toPrintable());

        System.out.println();
        ClassLayout intLayout = ClassLayout.parseInstance(new int[]{});
        System.out.println(intLayout.toPrintable());


        System.out.println();
        ClassLayout serviceLayout = ClassLayout.parseInstance(new ServerSocketService());
        System.out.println(serviceLayout.toPrintable());

        /**
         * Integer的内存大小：
         *
         * 开启指针压缩：int 基础类型=4字节，integer对象会在堆中分配内存，integer大小= 8字节（markword）+4字节（压缩后kclass地址）+4字节（int数据）=16字节
         * 关闭指针压缩：integer大小= 8字节（markword）+8字节（未压缩后kclass地址）+4字节（int数据）+ 4字节（padding补齐）=24字节
         */

        System.out.println();
        Integer a = 12;
        ClassLayout Integer = ClassLayout.parseInstance(a);
        System.out.println(Integer.toPrintable());
    }
}
