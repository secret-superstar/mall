package com.sp.tencent;

import java.util.HashMap;
import java.util.Map;

public class JvmCase {


    public JvmCase() {

    }

    //对内存数量进行计算处理
    public Map<String, Integer> getHeapInfo() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int total = getJvmHeapNum();
        int edenNum = new Eden().getMemoryNum();
        int oldMemoryNum = new OldMemory().getMemoryNum();
        int permMemoryNum = new Perm().getMemoryNum();

        //根据内存的信息堆类回收操作，如果配置的区域小于数据怎么来处理

        map.put("eden", edenNum);
        map.put("old", oldMemoryNum);
        map.put("perm", permMemoryNum);
        return map;
    }

    private int getJvmHeapNum() {
        return 0;
    }

    public void printCurrentMemoryInfo() {
        Map<String, Integer> map = getHeapInfo();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        JvmCase jvmCase = new JvmCase();
        jvmCase.printCurrentMemoryInfo();
    }
}


/**
 * 新生代
 */
class Eden implements Memory {

    public int getMemoryNum() {
        return 0;
    }
}

/**
 * 年老代
 */
class OldMemory implements Memory {
    public int getMemoryNum() {
        return 2;
    }
}

/**
 * 持久代
 */
class Perm implements Memory {
    public int getMemoryNum() {
        return 3;
    }
}