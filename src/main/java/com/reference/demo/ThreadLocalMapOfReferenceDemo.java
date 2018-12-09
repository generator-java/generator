package com.reference.demo;

import java.lang.reflect.*;

/**
 * 启发来源：https://plumbr.io/blog/locked-threads/how-to-shoot-yourself-in-foot-with-threadlocals
 * 参考图（并未解释清楚，图画的很好所有被大量转载）： http://www.importnew.com/22039.html
 * <p>
 * 如果其中一个应用程序类在ThreadLocal变量中存储一个值，并且在完成手头的任务后没有删除它，那么该对象的副本将保留在Thread（来自应用程序服务器线程池）中。
 * 由于池化线程的生命周期超过应用程序的生命周期，因此它将阻止对象，因此ClassLoader负责加载应用程序以进行垃圾回收。我们创建了一个泄漏，
 * 它有机会在一个好的旧java.lang.OutOfMemoryError：PermGen空间形式中浮出水面。
 * <p>
 * 那么考虑到它可能造成的伤害，我们应该避免使用ThreadLocal吗？也许没有那么快约书亚布洛赫在五年前说过：
 * <p>
 * “你能用本地线程导致意外的物体保留吗？你当然可以。但你也可以用数组做到这一点。这并不意味着线程本地（或数组）是坏事。
 * 仅仅是你必须小心使用它们。线程池的使用需要特别小心。如同在许多地方已经注意到的那样，粗略地使用线程池以及粗略使用线程本地可能会导致意外的对象保留。但把责任归咎于本地线程是没有根据的。“
 * <p>
 * ----------------volatile---static---ThreadLocal-----------------------
 * 1: 根据java规范，每个线程放保存的对象，都是线程的副本，所有叫这里命名为ThreadLocal copy
 * Java内存模型的抽象结构：---(JSR133)
 * a：Java线程之间的通信由Java内存模型控制，JMM决定一个线程对共享变量的写入何时对另一个线程可见。
 * b: JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory），
 * 本地内存中存储了该线程以读写共享变量的副本。
 * 2:
 * ---MainThread-------ThreadLocal---copy1---------start---------------------------
 * <p>
 * ---Thread1----------ThreadLocal---copy2---------start---------------------------
 * <p>
 * ---Thread2----------ThreadLocal---copy3---------start---------------------------
 *
 * problem---rec
 */
public class ThreadLocalMapOfReferenceDemo {
    private volatile static ThreadLocal<String> threadLocal = new ThreadLocal<>();
//    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Thread thread = new Thread(() -> new ThreadLocalMapOfReferenceDemo().myEntryChainCheck(), "====myThread1");
        thread.start();
//
        thread.join();
        Thread.sleep(1000L);
        Thread thread1 = new Thread(() -> new ThreadLocalMapOfReferenceDemo(thread).myEntryChainCheck2(), "----myThread2");
        thread1.start();
        thread1.join();

//        thread = null;
        thread1 = null;
        System.gc();
        threadLocal.get();

        Method getMap = threadLocal.getClass().getDeclaredMethod("getMap", Thread.class);
        getMap.setAccessible(true);
        Object threadLocalMap = getMap.invoke(threadLocal, Thread.currentThread());

        getTable(threadLocalMap);

    }

    private static void getTable(Object threadLocalMap) throws NoSuchFieldException, IllegalAccessException {
        Field size = threadLocalMap.getClass().getDeclaredField("table");
        size.setAccessible(true);
        Object[] o = (Object[]) size.get(threadLocalMap);
        System.out.println(o.length);
        for (Object o1 : o) {
            if (o1 != null) {
                System.out.println(o1);
            }
        }
    }

    Thread thread;

    public ThreadLocalMapOfReferenceDemo(Thread thread) {
        this.thread = thread;
    }

    public ThreadLocalMapOfReferenceDemo() {
    }

    private void myEntryChainCheck() {
        threadLocal.set("xxxx");
//        threadLocal2.set("xxxx--------m1");
        System.out.println("before gc: variable-1=" + threadLocal.get());
//        threadLocal.remove();
        System.gc();
        // only r.get() becomes null
        String s = threadLocal.get();
        System.out.println("after gc: variable-1=" + s);
    }

    private void myEntryChainCheck2() {
        threadLocal.set("222222");
        System.out.println("before gc: variable-1=" + threadLocal.get());
//        System.gc();
        // only r.get() becomes null
        String s = threadLocal.get();

        try {
            Method getMap = threadLocal.getClass().getDeclaredMethod("getMap", Thread.class);
            getMap.setAccessible(true);
            Object threadLocalMapInstance = getMap.invoke(threadLocal, Thread.currentThread());

            Method[] ms = threadLocalMapInstance.getClass().getDeclaredMethods();
            Method getEntry = ms[6];
            Method set = ms[1];
            getEntry.setAccessible(true);
            set.setAccessible(true);
            for (int i = 0; i < 2000; i++) {
                String format = String.format("reflect_set->%d", i);
                set.invoke(threadLocalMapInstance, new ThreadLocal<>(), format);
            }
            Object invoke = getEntry.invoke(threadLocalMapInstance, threadLocal);
            System.out.println(invoke);

            getTable(threadLocalMapInstance);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}