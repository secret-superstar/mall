package com.sp.practice.play;


/**
 * JAVA虚拟机解决的问题
 * 描述：
 * 1. 对于底层进行封装，包括对于数据结构和类型的封装
 * 2. 能够调用底层的资源和服务，包括线程，进程，定时任务等
 * 3. 能够进行内存的处理，如字符串，数组，集合等，内存的操作
 * 4. 序列化的处理： 文件操作，数据库操作，IO流操作
 * 5. 内存分配的算法了解
 * 6. 数据库的处理，基本上服务于业务的开发
 * 7. 技术的价值体系在哪里？ 怎么来看自己能力的实现，还是自己造轮子？
 * 8. JAVA可以构建成一个阿里体系，底层还是及其强大的。
 * 9. JAVA的中台架构的搭建，并且对于架构中的每一块领域进行深入的分析和了解。
 * 10. JAVA侧重于架构和数据。 那就重点把JAVA的架构构建完美，并且使用各种大数据的操作处理。
 */
public class Play {


    public static void main(String[] args) {
        System.out.println("Hello World !");

        Play play  = new Play();
        String info  = play.getInfo(23, "q2323");
        System.out.println(info);

    }

    String getInfo(Integer age, String name) {
        StringBuffer sb = new StringBuffer();

        sb.append(age + ",");
        sb.append(name + ",");
        return sb.toString();
    }



}
