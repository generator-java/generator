package com.reference.demo;

/**
 * 版权声明：本文章是作者辛勤书写的成果，如需转载，请与作者联系，并保留作者信息以及原文链接，谢谢~~
 * 引言： 逃逸分析（Escape Analysis)是众多JVM技术中的一个使用不多的技术点，本文将通过一个实例来分析其使用场景。
 * ---------------------
 * 作者：bladestone
 * 来源：CSDN
 * 原文：https://blog.csdn.net/blueheart20/article/details/76167489
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 * 通过逃逸分析来决定某些实例或者变量是否要在堆中进行分配，如果开启了逃逸分析，
 * 即可将这些变量直接在栈上进行分配，而非堆上进行分配。这些变量的指针可以被全局所引用，或者其其它线程所引用。
 */
public class OnStackTest {
    public static void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;
    }

    public static void main(String[] args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }
}
