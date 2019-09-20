package com.sp.practice.containers;// containers/Ex12.java
// TIJ4 Chapter Containers, Exercise 12, page 833
// Substitute a HashMap, a TreeMap and a LinkedHashMap
// in AssociativeArray.java's main().

import java.util.*;
import java.util.regex.Pattern;

public class Ex12<K, V> {
    private Object[][] pairs;
    private int index;

    public Ex12(int length) {
        pairs = new Object[length][2];
    }

    public void put(K key, V value) {
        if (index >= pairs.length)
            throw new ArrayIndexOutOfBoundsException();
        pairs[index++] = new Object[]{key, value};
    }

    /**
     * 对数组进行申请空间，把key 作为序号0 ， value作为序号1
     *
     * @param key
     * @param value
     */
    public void put2(K key, V value) {
        if (index > pairs.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        pairs[index++] = new Object[]{key, value};
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        for (int i = 0; i < index; i++)
            if (key.equals(pairs[i][0]))
                return (V) pairs[i][1];
        return null; // Did not find key
    }


    /**
     * 通过数组来查找，根据数组0表示key， 数组1表示value
     *
     * @param key
     * @return
     */
    public V get2(K key) {
        for (int i = 0; i < index; i++) {
            if (key.equals(pairs[i][0])) {
                return (V) pairs[i][1];
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < index; i++) {
            result.append(pairs[i][0].toString());
            result.append(" : ");
            result.append(pairs[i][1].toString());
            if (i < index - 1)
                result.append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Ex12<String, String> map =
                new Ex12<String, String>(6);
        map.put("sky", "blue");
        map.put("grass", "green");
        map.put("ocean", "dancing");
        map.put("tree", "tall");
        map.put("earth", "brown");
        map.put("sun", "warm");
        try {
            map.put("extra", "object"); // Past the end
        } catch (ArrayIndexOutOfBoundsException e) {
            print("Too many objects");
        }


        Ex12<String, String> map2 = new Ex12<>(4);
        map2.put("sk2", "blue");
        map2.put("grass2", "green");
        map2.put("sun2", "warm");
        print(map2);


        print(map);
        print(map.get("ocean"));
        HashMap<String, String> hashMap =
                new HashMap<String, String>(6);
        hashMap.put("sky", "blue");
        hashMap.put("grass", "green");
        hashMap.put("ocean", "dancing");
        hashMap.put("tree", "tall");
        hashMap.put("earth", "brown");
        hashMap.put("sun", "warm");

        HashMap<String, String> hashMap2 = new HashMap<String, String>(7);
        hashMap2.put("sky2", "blue2");
        hashMap2.put("grass2", "green");

        try {
            hashMap.put("extra", "object"); // Past the end
        } catch (ArrayIndexOutOfBoundsException e) {
            print("Too many objects");
        }
        print(hashMap);
        print(hashMap.get("ocean"));
        TreeMap<String, String> treeMap =
                new TreeMap<String, String>();
        treeMap.put("sky", "blue");
        treeMap.put("grass", "green");
        treeMap.put("ocean", "dancing");
        treeMap.put("tree", "tall");
        treeMap.put("earth", "brown");
        treeMap.put("sun", "warm");
        try {
            treeMap.put("extra", "object"); // Past the end
        } catch (ArrayIndexOutOfBoundsException e) {
            print("Too many objects");
        }
        print(treeMap);
        print(treeMap.get("ocean"));
        LinkedHashMap<String, String> linkedHashMap =
                new LinkedHashMap<String, String>(6);
        linkedHashMap.put("sky", "blue");
        linkedHashMap.put("grass", "green");
        linkedHashMap.put("ocean", "dancing");
        linkedHashMap.put("tree", "tall");
        linkedHashMap.put("earth", "brown");
        linkedHashMap.put("sun", "warm");
        try {
            linkedHashMap.put("extra", "object"); // Too far
        } catch (ArrayIndexOutOfBoundsException e) {
            print("Too many objects");
        }
        print(linkedHashMap);
        print(linkedHashMap.get("ocean"));

        System.out.println("------------- out three iter to map --------------------");
        iter1(hashMap);
        iter2(hashMap);
        iter3(hashMap);

        System.out.println("-------------  collection ----------------------");
        collection1();

        System.out.println("-------------  set ----------------------");
        set1();
    }

    public static void print(Object obj) {
        System.out.println(obj.toString());
    }

    public static void iter1(Map<String, String> map) {

        System.out.println("iter1 .....");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String keys = entry.getKey();
            String values = entry.getValue();
            System.out.println("key :" + keys + ", value :" + values);
        }
    }

    public static void iter2(Map<String, String> map) {
        System.out.println("iter2 .....");

        for (String keys : map.keySet()) {
            String values = map.get(keys);
            System.out.println("key :" + keys + ", value :" + values);
        }
    }

    public static void iter3(Map<String, String> map) {

        System.out.println("iter3 .....");
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String keys = iter.next();
            String values = map.get(keys);
            System.out.println("key :" + keys + ", value :" + values);
        }
    }

    public static void println(Object obj) {
        System.out.println(obj.toString());
    }

    public static void collection1() {
        List<String> al = new ArrayList<String>();
        List<String> ll = new LinkedList<String>();
        for (int i = 0; i < Countries.DATA.length; i++) {
            al.add(Countries.DATA[i][0]);
            ll.add(Countries.DATA[i][1]);
        }
        Collections.sort(al);
        Collections.sort(ll);
        println("Countries: " + al);
        println("Capitals: " + ll);
        for (int i = 0; i < 2; i++) {
            Collections.shuffle(al);
            Collections.shuffle(ll);
            println("Countries " + i + ": " + al);
            println("Capitals " + i + ": " + ll);
        }
        List<String> al2 = new ArrayList<String>();
        List<String> ll2 = new LinkedList<String>();
        for (int i = 0; i < 2; i++) {
            al2.add(Countries.DATA[i][0]);
            ll2.add(Countries.DATA[i][1]);
        }
        for (int i = 0; i < 2; i++) {
            Collections.shuffle(al2);
            Collections.shuffle(ll2);
            println("Countries " + i + ": " + al2);
            println("Capitals " + i + ": " + ll2);
            Collections.sort(al2);
            Collections.sort(ll2);
        }

        Collections.shuffle(al2);
        Collections.sort(al2);
    }

    public static void set1() {
        Set<String> hs = new HashSet<String>();
        Set<String> lhs = new LinkedHashSet<String>();
        Set<String> ts = new TreeSet<String>();
        println("HashSet hs = " + hs);
        println("LinkedHashSet lhs = " + lhs);
        println("TreeSet ts = " + ts);
        Map<String, String> hm = new HashMap<String, String>(0);
        Pattern p = Pattern.compile("Z[a-zA-Z]*");
        for (int i = 0; i < Countries.DATA.length; i++) {
            if (p.matcher(Countries.DATA[i][0]).matches())
                hm.put(Countries.DATA[i][0], Countries.DATA[i][0]);
        }
        println("hm.keySet() = " + hm.keySet());
        println("Adding hm.keySet()");
        hs.addAll(hm.keySet());
        lhs.addAll(hm.keySet());
        ts.addAll(hm.keySet());
        println("hs: " + hs);
        println("lhs: " + lhs);
        println("ts: " + ts);
        println("Adding 10 more times");
        for (int i = 0; i < 10; i++) {
            hs.addAll(hm.keySet());
            lhs.addAll(hm.keySet());
            ts.addAll(hm.keySet());
        }
        println("hs: " + hs);
        println("lhs: " + lhs);
        println("ts: " + ts);
    }
}

