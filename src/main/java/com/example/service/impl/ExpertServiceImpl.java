package com.example.service.impl;

import com.example.service.UnitExpertSerice;
import com.example.service.UnitService;
import com.example.utils.ExcelUtil;
import com.example.utils.PasswordUtil;
import com.example.utils.SortUtil;
import com.example.vo.BookVo;
import com.example.vo.ExpertVo;
import com.example.mapper.ExpertMapper;
import com.example.service.ExpertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.UnitExpertVo;
import com.example.vo.UnitVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertMapper, ExpertVo> implements ExpertService {
    @Autowired
    ExpertMapper expertMapper;
    @Autowired
    UnitService unitService;
    @Autowired
    ExpertService expertService;
    @Autowired
    UnitExpertSerice unitExpertSerice;


    /**
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
       //存储的是每一行,工具类为我们设置好的模板 每行有21列
        List<Object[]> list =   ExcelUtil.importExcel(fileName,file);

        for(int i = 0;i<list.size();i++){
            Object[] tmp_objects = list.get(i);

            if (tmp_objects[14]==null||tmp_objects[14]=="error"){
                throw new Exception("导入失败(第"+(i+1)+"行,电话未填写，或单元格损坏)");
            }
            String phone = tmp_objects[14]+"";
            if(phone.length()!=11){
                throw new Exception("导入失败(第"+(i+1)+"行,电话格式不是11位)");
            }

            if (tmp_objects[0]==null||tmp_objects[0]=="error"){
                throw new Exception("导入失败(第"+(i+1)+"行,姓名未填写，或单元格损坏)");
            }
            String name = tmp_objects[0]+"";
            if (tmp_objects[2]==null||tmp_objects[2]=="error"){
                throw new Exception("导入失败(第"+(i+1)+"行,高校未填写,或单元格损坏)");
            }
            String school = tmp_objects[2]+"";

            String role = "";//可以为空职称
            if(tmp_objects[16]== "error")
            {
                throw new Exception("导入失败(第"+(i+1)+"行,单元格损坏)");
            }
           else{
               role = tmp_objects[16]+"";
            }
           String unit = tmp_objects[6]+"";

            /**
             * s
             * */
            String sex = "";
            if(tmp_objects[17]!=null){
             sex  = tmp_objects[17]+"";
            }

            /**
             * 获取专家省份 市区
             * */
            String province = tmp_objects[3]+"";
            if(tmp_objects[3]==null||tmp_objects[3].equals( "error"))
            {
                throw new Exception("导入失败(第"+(i+1)+"行,专家省份错误)");
            }


            /**
             * 获取专家学校是中央还是
             * */

            String school_type = tmp_objects[4]+" ";
            if(tmp_objects[4]==null||tmp_objects[4].equals( "error"))
            {
                throw new Exception("导入失败(第"+(i+1)+"行,专家高校信息错误)");
            }




            /**
             * 获取专家专业1
             * */

            String major1 = "";
            major1 = tmp_objects[8]+"";

            String major2 = "";
            major2 = tmp_objects[10]+"";

            String major3 = "";

            major3 = tmp_objects[12]+"";

            String course1 = "";
            course1 = tmp_objects[9]+"";


            String course2 = "";
            course2 = tmp_objects[11]+"";

            String course3 = "";
            course3 = tmp_objects[13]+"";

            String job1 = "";
            job1 = tmp_objects[5]+"";

            String job2 = "";
            job2 = tmp_objects[7]+"";

            String birth = "";
            birth = tmp_objects[15]+"";

            String nation = "";
            nation = tmp_objects[19]+"";

            String pwd = "";
            pwd = tmp_objects[21]+"";



            /**
             * 专家政治面貌可以为空
             * */
            String politic = "";
            if(tmp_objects[18]!=null&&!tmp_objects[18].equals( "error"))
            {
               politic = tmp_objects[18]+"";
            }
            /**
             * 专家email可以为空
             * */
            String email = "";
            if(tmp_objects[20]!=null&&tmp_objects[20].equals( "error"))
            {
                email = tmp_objects[20]+"";
            }





            /**
             * word里的单位2
             * */
            if (tmp_objects[6]!=null&&!tmp_objects[6].equals("error")){
                String uni1 = (String) tmp_objects[6];
                UnitVo tmp_unit = unitService.getById(uni1);
                /**
                 * 若没有此单位 插入
                 * */
                if(tmp_unit==null){
                    System.out.println("单位插入");
                    tmp_unit = new UnitVo();
                    tmp_unit.setName(uni1);
                    try {
                        unitService.saveOrUpdate(tmp_unit);

                    }
                    catch (Exception e){
                        throw new Exception("导入失败(第"+(i+1)+"行,单位关系不正确)"+e);
                    }
                }

                /**
                 * 要更新中间关系表
                 * */
                UnitExpertVo unitExpertVo = new UnitExpertVo();
                unitExpertVo.setExpert_name(name);
                unitExpertVo.setUnit_name(unit);
                unitExpertVo.setId(phone+1);
                try{
                unitExpertSerice.saveOrUpdate(unitExpertVo);}
                catch (Exception e){
                    throw new Exception("导入失败(第"+(i+1)+"行,单位专家更新失败，请联系管理员)"+e);
                }
            }

            /**
             * 更新专家单位表
             * */
            UnitExpertVo unitExpertVo1 = new UnitExpertVo();
            unitExpertVo1.setExpert_name(name);
            unitExpertVo1.setUnit_name(school);
            unitExpertVo1.setId(phone+0);
            try{
                unitExpertSerice.saveOrUpdate(unitExpertVo1);}
            catch (Exception e){
                throw new Exception("导入失败(第"+(i+1)+"行,高校专家更新失败，请联系管理员)"+e);
            }

           phone = SortUtil.doStr(phone);
            name = SortUtil.doStr(name);
            school = SortUtil.doStr(school);
            role = SortUtil.doStr(role);
            email = SortUtil.doStr(email);
            school_type = SortUtil.doStr(school_type);
            politic = SortUtil.doStr(politic);
            province = SortUtil.doStr(province);
            phone = SortUtil.doStr(phone);
            major1 = SortUtil.doStr(major1);
            major2 = SortUtil.doStr(major2);
            major3 = SortUtil.doStr(major3);
            course1 = SortUtil.doStr(course1);
            unit = SortUtil.doStr(unit);
            course2 = SortUtil.doStr(course2);
            course3 = SortUtil.doStr(course3);
            pwd =SortUtil.doStr(pwd);
            job1 =SortUtil.doStr(job1);
            job2 =SortUtil.doStr(job2);
            nation =SortUtil.doStr(nation);

            ExpertVo expertVo = new ExpertVo();
            expertVo.setPhone(phone);
            expertVo.setName(name);
            expertVo.setSchool(school);
            expertVo.setRole(role);
            expertVo.setSex(sex);
            expertVo.setEmail(email);
            expertVo.setSchool_type(school_type);
            expertVo.setPolitic(politic);
            expertVo.setProvince(province);
            expertVo.setMajor1(major1);
            expertVo.setMajor2(major2);
            expertVo.setMajor3(major3);
            expertVo.setCourse1(course1);
            expertVo.setUnit(unit);
            expertVo.setCourse2(course2);
            expertVo.setCourse3(course3);
            expertVo.setJob1(job1);
            expertVo.setJob2(job2);
            expertVo.setNation(nation);
            expertVo.setPwd(pwd);
            try {
                expertService.saveOrUpdate(expertVo);
                System.out.println("成功");
            }
            catch (Exception e){
                throw new Exception("更新错误："+e);
            }

  //          ExpertVo tmpVo = expertMapper.selectById(phone);

//            if(tmpVo==null){
//                try {
//
//                    expertMapper.insert(expertVo);
//
//                }
//              catch (Exception e){
//                    throw new Exception("位置"+(i-1)+"插入错误，控制台报错："+e);
//              }
//            }
//            if(tmpVo!=null){
//                try {
//                    System.out.println("走到了更新");
//                    expertMapper.updateById(expertVo);
//                    System.out.println("走完了");
//                }
//                catch (Exception e){
//                    throw new Exception("位置"+(i-1)+"未知原因更新错误");
//                }
//            }


        }//获取每一行的元素



        return false;
    }

    @Override
    public boolean deleteAll() {
        expertMapper.deleteAll();
        return false;
    }

    @Override
    public List<ExpertVo> fuzzySeach(String name) {
        try {
            name +="%";
            return     expertMapper.fuzzySearch(name);
        }
        catch (Exception e){
            System.out.println("课程模糊查询发生错误"+e);
            return null;
        }
    }

    @Override
    public void export(HttpServletResponse response, String fileName) throws Exception {
        List<List<Object>> list;
        try {
            List<ExpertVo> expertVoList = expertService.list();//查出数据库数据
            list    = ExcelUtil.voToList(expertVoList);
        } catch ( Exception e) {
            throw new Exception("解析教材时，发生错误"+e);
        }

        List<String> cellName = new ArrayList<>();
        cellName.add("*姓名");
        cellName.add("组号（-1为拉黑，0为未分组）");
        cellName.add("学校*（*为必填）");
        cellName.add("省份*");
        cellName.add("学校类型*");
        cellName.add("职务1");
        cellName.add("单位2");
        cellName.add("职务2");
        cellName.add("专业类1*");
        cellName.add("课程/方向1");
        cellName.add("专业类2");
        cellName.add("课程/方向2");
        cellName.add("专业类3");
        cellName.add("课程/方向2");
        cellName.add("电话*");
        cellName.add("出生年份");
        cellName.add("职称");
        cellName.add("性别");
        cellName.add("政治面貌");
        cellName.add("民族");
        cellName.add("邮箱");
        cellName.add("密码");

        try{   ExcelUtil.uploadExcelAboutUser(response,fileName
                ,cellName,list);
        }
        catch (Exception e){
            throw  new Exception("导出发生异常"+e);
        }
    }

    @Override
    public void exportPwd(HttpServletResponse response, String fileName) throws Exception {


        List<List<Object>> list;
        try {
            List<ExpertVo> expertVoList = expertService.list();//查出数据库数据
            list    = ExcelUtil.voToList(expertVoList);
        } catch ( Exception e) {
            throw new Exception("解析教材时，发生错误"+e);
        }

        List<String> cellName = new ArrayList<>();
        cellName.add("*姓名");
        cellName.add("组号（-1为拉黑，0为未分组）");
        cellName.add("学校*（*为必填）");
        cellName.add("省份*");
        cellName.add("学校类型*");
        cellName.add("职务1");
        cellName.add("单位2");
        cellName.add("职务2");
        cellName.add("专业类1*");
        cellName.add("课程/方向1");
        cellName.add("专业类2");
        cellName.add("课程/方向2");
        cellName.add("专业类3");
        cellName.add("课程/方向2");
        cellName.add("电话*");
        cellName.add("出生年份");
        cellName.add("职称");
        cellName.add("性别");
        cellName.add("政治面貌");
        cellName.add("民族");
        cellName.add("邮箱");
        cellName.add("密码");

        try{   ExcelUtil.uploadExcelAboutUser(response,fileName
                ,cellName,list);
        }
        catch (Exception e){
            throw  new Exception("导出发生异常"+e);
        }


    }


    @Override
    public void setPwd() throws Exception {
        try {
            List<ExpertVo> list = expertMapper.listNoPwd();
            for(ExpertVo expertVo : list){
                expertVo.setPwd(PasswordUtil.randomPassword(8));
                expertService.saveOrUpdate(expertVo);
            }

        }
        catch (Exception e){
            throw new Exception("设置密码发生错误"+e);

        }


    }


//    @Transactional(readOnly = false,rollbackFor = Exception.class)
//    @Override
//    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
//
//        boolean notNull = true;
//        List<ExpertVo> ExpertList = new ArrayList<ExpertVo>();
//        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//            throw new Exception("上传文件格式不正确");
//        }
//        boolean isExcel2003 = true;
//        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
//            isExcel2003 = false;
//        }
//        InputStream is = file.getInputStream();
//        Workbook wb = null;
//        if (isExcel2003) {
//            wb = new HSSFWorkbook(is);
//        } else {
//            wb = new XSSFWorkbook(is);
//        }
//        Sheet sheet = wb.getSheetAt(0);
//        if(sheet!=null){
//            notNull = true;
//        }
//        ExpertVo expertVo;
//        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
//            Row row = sheet.getRow(r);
//            if (row == null){
//                continue;
//            }
//
//            expertVo = new ExpertVo();
//
//
//
//
//            row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
//            String phone = row.getCell(14).getStringCellValue();
//            if(phone==null || phone.isEmpty()){
//                throw new Exception("导入失败(第"+(r+1)+"行,电话未填写)");
//            }
//
//            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//            if( row.getCell(0).getCellType() !=1){
//                throw new Exception("导入失败(第"+(r+1)+"行,姓名请设为文本格式)");}
//            String name = row.getCell(0).getStringCellValue();
//            if(name == null || name.isEmpty()){
//                throw new Exception("导入失败(第"+(r+1)+"行,姓名未填写)");
//            }
//
//            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//            String school = row.getCell(2).getStringCellValue();
//            if(school == null || school.isEmpty()){
//                throw new Exception("导入失败(第"+(r+1)+"行,学校未填写)");
//            }
//
//            row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
//            String role = "";//可以为空 职称
//            role += row.getCell(16).getStringCellValue();
//
//            int sex = 0;
//
//
//
//
//
//            expertVo.setName(name);
//            expertVo.setPhone(phone);
//            expertVo.setSchool(school);
//            expertVo.setRole(role);
//            expertVo.setSex(sex);//默认为0 男
//            ExpertList.add(expertVo);
//
//        }
//        for (ExpertVo experResord : ExpertList) {
//            //看看记录存在不
//           String phone = experResord.getPhone();
//       ExpertVo expertVO = expertMapper.selectById(phone);
//           if (expertVO==null){
//               try {
//                   expertMapper.insert(experResord);
//               }
//               catch (Exception e){
//                   throw new Exception("插入失败咯");
//               }
//
//           }
//           else{
//               try {
//               expertMapper.updateById(experResord);
//               }
//               catch (Exception e){
//                   throw new Exception("更新失败咯");
//               }
//           }
//
//        }
//
//        return notNull;
//    }


}
