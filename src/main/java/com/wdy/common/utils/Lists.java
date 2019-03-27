package com.wdy.common.utils;

import java.util.*;

/**
 * Created by hdy on 2018/4/20.
 */
public class Lists {

    /**
     * 去重(无序)
     *
     * @param list
     * @return
     */
    public static List outRepeat(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }


    /**
     * 去重(有序)
     *
     * @param list
     * @return
     */
    public static List outRepeatOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    /**
     * 并集
     *
     * @param list
     * @param list2
     * @return
     */
    public static List merge(List list, List list2) {
        list.addAll(list2);
        return list;
    }

    /**
     * 交集
     *
     * @param list
     * @param list2
     * @return
     */
    public static List intersection(List list, List list2) {
        list.retainAll(list2);
        return list;
    }

    /**
     * 差集
     *
     * @param list
     * @param list2
     * @return
     */
    public static List difference(List list, List list2) {
        list.removeAll(list2);
        return list;
    }

    /**
     * 无重复并集
     *
     * @param list
     * @param list2
     * @return
     */
    public static List noRepetitionMerge(List list, List list2) {
        list2.removeAll(list);
        list.addAll(list2);
        return list;
    }

    public static void main(String[] args) {
        String str = "xx,aaxx,656,12123135,123";
        String[] a = new String[str.split(",").length];
        for (int i = 0; i < str.split(",").length; i++) {
            a[i] = str.split(",")[i];
        }
        Logs.printHr(Arrays.asList(a).contains("aa"));

    }

}
