package com.example.utils;

import java.io.File;

public class DownloadUtil {
     public  static  File BookFile(){
         File file = new File("../static/jieba/stop_words.txt");         return  file;
     }
    public  static  File MajorFile(){
        File file = new File("../static/excel/Major.xlsx");
        return  file;
    }
    public  static  File courseFile(){
        File file = new File("../static/excel/course.xlsx");
        return  file;
    }
    public  static  File BookGroupingFile(){
        File file = new File("../static/excel/BookGrouping.xlsx");
        return  file;
    }


}
