package com.reference.demo;


import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 启发来源：https://plumbr.io/blog/locked-threads/how-to-shoot-yourself-in-foot-with-threadlocals
 * 本地线程变量，在使用线程池中框架中，容易发生内存泄漏，因为线程池使得ThreadLocalMap.Entry中的弱引用key存在强引用，无法被垃圾回收
 * problem
 *
 * author：staven
 */
public class WeakReferenceDemo extends ThreadLocal {

    public static void main(String[] args){
        //弱引用，模拟本地线程
        runThreadLocalMapEntry();
        System.out.println("==============================");
        //弱引用
        runWeakReference();
        System.out.println("==============================");
        //软引用
        runSoftReference();
        System.out.println("==============================");
        //虚引用
        runPhantomReference();

    }

    /**
     * 弱引用
     */
    private static void runWeakReference() {
        Thread th5 = new Thread();
        WeakReference<Thread> r = new WeakReference<>(th5);
        System.out.println("before gc: variable-3=" + r.get());
        Thread strongTh = th5;
        th5 = null;
        System.gc();
        System.out.println("after gc: variable-5=" + r.get());
    }
    /**
     * 模拟本地线程变量
     */
    private static void runThreadLocalMapEntry() {
        Thread th = new Thread();
        th.start();
        WeakReference<Thread> en = new ThreadLocalMap.Entry(th, new byte[1024 * 1024 * 1024 * 4]);
        System.out.println("before gc: variable-2=" + en.get());
        System.gc();
        System.out.println("after gc: variable-4=" + en.get());
    }

    /**
     * 软引用
     */
    private static void runSoftReference() {
        Thread th = new Thread();
        th.start();
        SoftReference<Thread> en = new SoftReference(th);
        System.out.println("before gc: variable-2=" + en.get());
        System.gc();
        System.out.println("after gc: variable-4=" + en.get());
    }

    /**
     * 虚引用
     */
    private static void runPhantomReference() {
        Thread th = new Thread();
        th.start();
        PhantomReference<Thread> en = new PhantomReference(th,new ReferenceQueue());
        System.out.println("before gc: variable-2=" + en.get());
        System.gc();
        System.out.println("after gc: variable-4=" + en.get());
    }

    static class ThreadLocalMap {
        static class Entry extends WeakReference<Thread> {
            Object value;

            Entry(Thread k, Object v) {
                super(k);
                value = v;
            }
        }
    }
}