package com.example.service;
import lombok.Data;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
public class PythonRun {
    private String environment = "E:\\Anaconda3\\envs\\transfomer_learn\\python.exe";
    private String root = null;
    private String cache = "C:\\Users\\Administrator\\Desktop\\所有的web开发任务\\";
    private boolean autoRemoveCache = true;

    public String run(String path, String ...args) throws IOException {
        path = createNewPy(path);
        List<String> inputArgs = new LinkedList<>(Arrays.asList(environment, path));  //设定命令行
        inputArgs.addAll(Arrays.asList(args));
        StringBuilder result = new StringBuilder();
        try {
            Process proc = Runtime.getRuntime().exec(inputArgs.toArray(new String[0]));  //执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
            in.close();
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (autoRemoveCache && path != null) {
            new File(path).delete();
        }
        return result.toString();
    }

    private String createNewPy(String path) throws IOException {
        File file = new File(path);
         if (file.isFile()){
            String result = loadTxt(file);
            if (root != null){
                result = "import sys\n" +
                        "sys.path.append(\"" + root + "\")\n" + result;
            }
            String save = cache + file.getName();
            saveTxt(save, result);
            return save;
        }
        return null;
    }

    private static File saveTxt(String filename, String string){
        File file = new File(filename);
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            out.write(string);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    private String loadTxt(File file){
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                result.append(str).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {

        // python文件路径
        String pyPath = "E:\\雷电预测\\各个版本\\2022_4_21\\train\\mytest.py";
        // 默认为python，如果使用了anaconda创建了环境，可以找到对应的路径并替换，类似于"E:\\Anaconda3\\envs\\xxx\\python.exe"。
        String pyEnvironment = "E:\\Anaconda3\\envs\\transfomer_learn\\python.exe";
        // 创建实例
        PythonRun pythonRun = new PythonRun();
        // 设置环境 置python项目的执行目录，若不设置，在调用了其它包时，可能会出现错误。如果没有import其它文件夹下的包或库，可以忽略。
        pythonRun.setEnvironment(pyEnvironment);
        pythonRun.setRoot("E:\\雷电预测\\各个版本\\2022_4_21\\pre");
        System.out.println(pythonRun.root);

        Runnable runnable = new Runnable() {
            @Override
            public void run()  {
                try {
                    System.out.println(pythonRun.run(pyPath, "1", "2"));

                }
                catch (Exception e) {

                }
            }
        };
        Thread t = new Thread(runnable);
        t.start();

        System.out.println("java is ok");
        // 参数为：(String path, String ...args)


    }
}

