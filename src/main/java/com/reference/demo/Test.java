package com.reference.demo;

public class Test {

    public static void main() {

        Worker worker = new Worker();

        while (true) {

            worker.useWorker();

        }

    }

}

class Worker {

    public Worker worker;

    public Worker getWorker() {

        return null == worker ? new Worker() : worker; //对象定义在方法体内部。

    }

    public void setWorker() {

        worker = new Worker();

    }


    public void useWorker() {

        Worker obj = getWorker();

    }


    public void useWorker2() {

        Worker obj = getWorker(); //没有被外部使用，不为内存逃逸

    }

}