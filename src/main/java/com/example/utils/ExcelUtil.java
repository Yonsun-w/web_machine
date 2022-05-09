package com.example.utils;



import com.example.vo.BookVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 路径：com.example.demo.utils
 * 类名：
 * 功能：导入导出
 * 备注：
 * 创建人：wjh
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
public class ExcelUtil {



    /**
     * 方法名：importExcel
     * 功能：导入
     * 描述：
     * 创建人：wjh
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static List<Object[]> importExcel(String fileName, MultipartFile file) {
        log.info("导入解析开始，fileName:{}",fileName);
        try {
            List<Object[]> list = new ArrayList<>();
             if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
            InputStream inputStream = file.getInputStream();
            Workbook wb = null;
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            //根据第一行获取表格的列数
            int cellnum=sheet.getRow(0).getPhysicalNumberOfCells();
            System.out.println(cellnum);
            //获取sheet的行数
            int rows = sheet.getPhysicalNumberOfRows();
            System.out.println("sheet文件有" + rows + "行");
            for (int i = 0; i < rows; i++) {
                //过滤表头行
                if (i == 0) {
                    continue;
                }
                //获取当前行的数据
                Row row = sheet.getRow(i);
                Object[] objects = new Object[cellnum];
                for (int index =0;index<objects.length;index++) {
                    Cell cell = row.getCell(index);
                    if(cell==null) {
                        objects[index] = null;
                        continue;}
                    //这里数值不知道怎么办，所以把数据全部转化为string
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    //若该列为空，也统一设置为null
                    if(cell.getStringCellValue().equals("")||
                    cell.getStringCellValue().equals("null")){
                        objects[index] = null;
                    }

                    objects[index] = cell.getStringCellValue();

                }
                list.add(objects);
            }
            log.info("导入文件解析成功！,list大小："+list.size());
            return list;
        }catch (Exception e){
            log.info("导入文件解析失败！");
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 用户信息导出类
     * @param response 响应
     * @param fileName 文件名
     * @param columnList 每列的标题名
     * @param dataList 导出的数据
     */
    public static void uploadExcelAboutUser(HttpServletResponse response,String fileName,List<String> columnList,List<List<Object>> dataList)
    throws Exception{
        XSSFWorkbook wb = new XSSFWorkbook();
        OutputStream outputStream =null;
        Sheet sheet = wb.createSheet(fileName);//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0

        for(int i = 0;i<columnList.size();i++){
            titleRow.createCell(i).setCellValue(columnList.get(i));//设置第一行的每一列
        }

        int cell = 1;
        for (List<Object> list : dataList) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
          for(int i = 0;i<columnList.size();i++)//根据列明把实体类依次打印出来，这里需要调整实体类的顺序，打印顺序和定义顺序应该是一样的？
          {
               row.createCell(i).setCellValue(list.get(i)+"");
               if((list.get(i)+"").equals("null")){
                  row.createCell(i).setCellValue(""); }
          }

          //  sheet.setColumnWidth(0, 256*3+184); 为该行的某一列设置宽度
            cell++;
        }
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e){
          throw new Exception(  "下载失败" + e );

        }

    }



public static void download(HttpServletResponse response,String uri,String fileName) throws Exception{
    ClassPathResource classPathResource = new ClassPathResource(uri);
    InputStream inputStream =classPathResource.getInputStream();


//            File file = new  File();
//            InputStream inputStream = new FileInputStream(file);

    //强制下载不打开
    response.setContentType("application/force-download");
    OutputStream out = response.getOutputStream();
    //使用URLEncoder来防止文件名乱码或者读取错误
    response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));

    int b = 0;
    byte[] buffer = new byte[1000000];
    while (b != -1) {
        b = inputStream.read(buffer);
        if(b!=-1) out.write(buffer, 0, b);
    }
    inputStream.close();
    out.close();
    out.flush();
}



    //测试导入
//    public static void main(String[] args) {
//        try {
//            String fileName = "f:/test.xlsx";
//            List<Object[]> list = importExcel(fileName);
//            for (int i = 0; i < list.size(); i++) {
//                User user = new User();
//                user.setId((Integer) list.get(i)[0]);
//                user.setUsername((String) list.get(i)[1]);
//                user.setPassword((String) list.get(i)[2]);
//                user.setEnable((Integer) list.get(i)[3]);
//                System.out.println(user.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }





//    public static void exportExcel(HttpServletResponse response, ExcelData data) {
//        log.info("导出解析开始，fileName:{}",data.getFileName());
//        try {
//            //实例化HSSFWorkbook
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            //创建一个Excel表单，参数为sheet的名字
//            HSSFSheet sheet = workbook.createSheet("sheet");
//            //设置表头
//            setTitle(workbook, sheet, data.getHead());
//            //设置单元格并赋值
//            setData(sheet, data.getData());
//            //设置浏览器下载
//            setBrowser(response, workbook, data.getFileName());
//            log.info("导出解析成功!");
//        } catch (Exception e) {
//            log.info("导出解析失败!");
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 方法名：setTitle
//     * 功能：设置表头
//     * 描述：
//     * 创建人：typ
//     * 创建时间：2018/10/19 10:20
//     * 修改人：
//     * 修改描述：
//     * 修改时间：
//     */
//    private static void setTitle(HSSFWorkbook workbook, HSSFSheet sheet, String[] str) {
//        try {
//            HSSFRow row = sheet.createRow(0);
//            //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
//            for (int i = 0; i <= str.length; i++) {
//                sheet.setColumnWidth(i, 15 * 256);
//            }
//            //设置为居中加粗,格式化时间格式
//            HSSFCellStyle style = workbook.createCellStyle();
//            HSSFFont font = workbook.createFont();
//
//            style.setFont(font);
//            style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
//            //创建表头名称
//            HSSFCell cell;
//            for (int j = 0; j < str.length; j++) {
//                cell = row.createCell(j);
//                cell.setCellValue(str[j]);
//                cell.setCellStyle(style);
//            }
//        } catch (Exception e) {
//            log.info("导出时设置表头失败！");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 方法名：setData
//     * 功能：表格赋值
//     * 描述：
//     * 创建人：wjh
//     * 修改人：
//     * 修改描述：
//     * 修改时间：
//     */
//    private static void setData(HSSFSheet sheet, List<String[]> data) {
//        try{
//            int rowNum = 1;
//            for (int i = 0; i < data.size(); i++) {
//                HSSFRow row = sheet.createRow(rowNum);
//                for (int j = 0; j < data.get(i).length; j++) {
//                    row.createCell(j).setCellValue(data.get(i)[j]);
//                }
//                rowNum++;
//            }
//            log.info("表格赋值成功！");
//        }catch (Exception e){
//            log.info("表格赋值失败！");
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 方法名：setBrowser
//     * 功能：使用浏览器下载
//     * 描述：
//     * 创建人：wjh
//     * 修改人：
//     * 修改描述：
//     * 修改时间：
//     */
//    private static void setBrowser(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
//        try {
//            //清空response
//            response.reset();
//            //设置response的Header
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            OutputStream os = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/vnd.ms-excel;charset=gb2312");
//            //将excel写入到输出流中
//            workbook.write(os);
//            os.flush();
//            os.close();
//            log.info("设置浏览器下载成功！");
//        } catch (Exception e) {
//            log.info("设置浏览器下载失败！");
//            e.printStackTrace();
//        }
//
//    }








    /**
     * 功能分析：把对象集合转换成数组集合
     *
     * @param list：需要操作的实体类集合
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static   List<List<Object>> voToList(List list) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //设置用到的变量参数
        String name = null, type = null, m_name = null;
        Object value = null;
        //创建一个二维字符串数组存放数据
        List<List<Object>> list1 = new ArrayList<>();
        //获取实体类方法操作对象
        Method m = null;
        //判断要提取实体类属性数据的集合是否为空
        if (list != null){
            //遍历集合中的实体类
            for (int y=0; y<list.size(); y++){
                //获取每一个实体类
                Object object = list.get(y);
                //获取实体类的所有属性，返回Field数组
                Field[] fields = object.getClass().getDeclaredFields();
                //创建一个数组存放实体类属性数据
                List<Object> str = new ArrayList<>();
                //遍历实体类的所有属性
                for (int i=0; i<fields.length; i++){
                    name = fields[i].getName();//获取属性的名字
                    m_name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
                    type = fields[i].getGenericType().toString();    //获取属性的类型
                    System.out.println(type);
                    //判断属性的类型修改数据结构
                    switch (type){

                        case "class java.lang.String":
                            m = object.getClass().getMethod("get"+m_name);  //组装getter方法
                            value = (String) m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){
                                /**
                                 * 你可以进行附加操作
                                 */
                            }
                            break;
                        case "class java.lang.Double":
                            m = object.getClass().getMethod("get"+m_name);
                            value =  m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){
                            }
                            break;
                        case "class java.lang.Boolean":
                            m = object.getClass().getMethod("get"+m_name);  //组装getter方法
                            value = m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){
                            }
                            break;
                        case "class java.lang.Integer":
                            m = object.getClass().getMethod("get"+m_name);
                            value = m.invoke(object); //调用getter方法获取属性值
                            if (value != null){

                            }
                            break;
                        case "class java.util.Date":
                            m = object.getClass().getMethod("get"+m_name);
                            value = m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){
                                value = new SimpleDateFormat("yyyy-MM-dd").format(value);//改变时间的格式
                            }
                            break;
                        case "class java.lang.Float":
                            m = object.getClass().getMethod("get"+m_name);
                            value = m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){

                            }
                            break;
                        case "class java.lang.Long":
                            m = object.getClass().getMethod("get"+m_name);
                            value = m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){
                            }
                            break;
                        case "class java.lang.Byte":
                            m = object.getClass().getMethod("get"+m_name);
                            value = m.invoke(object);    //调用getter方法获取属性值
                            if (value != null){

                            }
                            break;
                    }
                    //把实体类中的属性存放到一个字符串数组中
                    str.add(value);
                }
                //把字符串数组存放到集合中
                list1.add(str);
            }
        }
        return list1;
    }


}