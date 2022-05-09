package com.example.utils;

import java.util.*;
/**
 *自己写的一写工具 用来处理字符串之类的
 */
public class SortUtil {

    /**
     * 求Map<K,V>中Value(值)的最小值
     *
     * @param map
     * @return
     */
    public static Object getMaxValue(Map<String, Integer> map) {
        if (map == null)
            return null;
        Set<String> set=map.keySet();
        map.entrySet();
        List<Map.Entry<String,Integer>> list = new ArrayList(map.entrySet());
        Collection<Integer> c = map.values();

        Collections.sort
                (list, (o1, o2) -> (o2.getValue() - o1.getValue()));//升序
        return list.get(0).getKey();
    }


    /**
     * 处理字符串 拼接字符串 便于模糊查询 例如 刑事法 拼接为 刑%法  返回长度为3
     */
    public static String cutStr (String str) {
        if (str.length()<=2)
            return str;
       String res = str.charAt(0)+"%"+str.charAt(str.length()-1);
       return res;
    }


    /**
     * 把String 字符串的 括号去掉
     */
    public static String cutStrKuohao (String str) {
        if (str.length()<=2)
            return str;
        int index = 0;
        for(int i = 0;i<str.length();i++){
            index = i;
            if (str.charAt(i)=='('){
                break;
            }
        }
        if(index== str.length()-1){
            index ++;
        }
        String res = str.substring(0,index);
        return res;
    }


    /**
     * 把String null 转为 ""
     */
    public static String doStr (String str) {
       if(str.equals("null")) return "";
       return  str;
    }


    /**
     * @param str
     * @return 返回一个str 以 ,分割的list
     */
    public static List<String> toList1(String str){
    return Arrays.asList(str.split(","));
    }




    /**
     * @param str
     * @return 返回一个str 以、分割的list 并且如果有 - 把他去除
     */
    public static List<String> toList2(String str){
        return Arrays.asList(str.split("、|-"));
    }



    /**
     * 获取专业大类的方法
     * @param str 'xx类xxx'
     * @return 返回一个str  把他切断为xx类
     */
    public static String toList3(String str){
        return  Arrays.asList(str.split("类")).get(0);



    }


}
