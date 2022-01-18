package com.qxx.thirdservice.juc;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class TestVarHandle {

    int x = 8;

    // jdk9出现的，普通属性也可以进行原子操作，比反射快，直接操纵二进制码
    private static VarHandle handle;

    static {
        try {
            /** x指向8，VarHandle可以获取 8 的引用，换句话说就是x指向8，VarHandle也能指向8， 并且和x指向的是同一个实例  */
            handle = MethodHandles.lookup().findVarHandle(TestVarHandle.class, "x" , int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestVarHandle testVarHandle = new TestVarHandle();

        System.out.println(handle.get(testVarHandle));

        handle.set(testVarHandle, 9);
        System.out.println(testVarHandle.x);

        // 普通属性，也可以完成院子操作。
        handle.compareAndSet(testVarHandle,9,10);
        System.out.println(testVarHandle.x);

        handle.getAndAdd(testVarHandle, 10);
        System.out.println(testVarHandle.x);
    }
}
