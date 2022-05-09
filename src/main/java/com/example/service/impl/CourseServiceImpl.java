package com.example.service.impl;

import com.example.utils.ExcelUtil;
import com.example.utils.SortUtil;
import com.example.vo.BookVo;
import com.example.vo.CourseVo;
import com.example.mapper.CourseMapper;
import com.example.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, CourseVo> implements CourseService {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseMapper courseMapper;

    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
       try{ courseMapper.deleteAll();}
       catch (Exception e){
           throw  new Exception("在导入课程清空的这一阶段发生错误"+e);
       }
        List<Object[]> list = ExcelUtil.importExcel(fileName,file);
        for(int i = 0 ;i<list.size();i++){
            Object[] tmpObject = list.get(i);
            String type = tmpObject[0]+"";
            String year = tmpObject[1]+"";
            String name = tmpObject[2]+"";
            String school = tmpObject[3]+"";
            String admin  = tmpObject[4]+"";
            String province = tmpObject[5]+"";
            String majorClass = tmpObject[6]+"";
            String plat = tmpObject[7]+"";
            String Cclass = tmpObject[8]+"";
            String Ctype = tmpObject[9]+"";
            String peopleMajor = tmpObject[10]+"";
            String people = tmpObject[11]+"";
            String area = tmpObject[12]+"";
            String Rarea = tmpObject[13]+"";
            String flag = tmpObject[14]+"";
        if(tmpObject[2]==null||tmpObject[3]==null||tmpObject[0]==null||
        admin==null||tmpObject[6]==null||tmpObject[5]==null){

            throw new Exception("第"+(i+1)+"行信息缺乏"
            +"\n 若您的数据条数没有上方提示的条数，请考虑是否您的excel文件" +
                    "存在隐藏数据，这可能会导致识别错误：可以选中最后一条数据集下的" +
                    "所有空单元格，删除他们");

        }

        CourseVo courseVo = new CourseVo();


            type = SortUtil.doStr(type);
            year = SortUtil.doStr(year);
            name = SortUtil.doStr(name);
            school = SortUtil.doStr(school);
            admin = SortUtil.doStr(admin);
            people = SortUtil.doStr(people);
            peopleMajor = SortUtil.doStr(peopleMajor);
            majorClass = SortUtil.doStr(majorClass);
            province = SortUtil.doStr(province);
            Cclass = SortUtil.doStr(Cclass);
            Ctype = SortUtil.doStr(Ctype);
            area = SortUtil.doStr(area);
            Rarea = SortUtil.doStr(Rarea);
            flag = SortUtil.doStr(flag);



        courseVo.setType(type);
        courseVo.setYear(year);
        courseVo.setName(name);
        courseVo.setSchool(school);
        courseVo.setAdmin(admin);
        courseVo.setPeople(people);
        courseVo.setPeopleMajor(peopleMajor);
        courseVo.setMajorClass(majorClass);
        courseVo.setProvince(province);
        courseVo.setPlat(plat);
        courseVo.setCclass(Cclass);
        courseVo.setCtype(Ctype);
        courseVo.setArea(area);
        courseVo.setRarea(Rarea);
        courseVo.setFlag(flag);

     try {
         courseService.saveOrUpdate(courseVo);
     }
     catch (Exception e){
         throw new Exception("发生课程批量插入错误："+e);
     }

        }

        return false;
    }

    @Override
    public List<CourseVo> fuzzySeach(String name) {
        try {
            name +="%";
        return   courseMapper.fuzzySearch(name);
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
            List<CourseVo> bookList = courseService.list();
            list = ExcelUtil.voToList(bookList);
        } catch ( Exception e) {
            throw new Exception("解析课程时，发生错误"+e);
        }

        List<String> cellName = new ArrayList<>();
        cellName.add("课程分类");
        cellName.add("申报年份");
        cellName.add("*课程名称");
        cellName.add("*学校 ");
        cellName.add("*负责人");
        cellName.add("*省份");
        cellName.add("*专业类");
        cellName.add("主要开课平台");
        cellName.add("课程类型");
        cellName.add("课程类别");
        cellName.add("团队成员第一人");
        cellName.add("课程主要团队成员（包含负责人）");
        cellName.add("领域");
        cellName.add("推荐领域");
        cellName.add("组号");
        try{
            ExcelUtil.uploadExcelAboutUser(response,fileName
                ,cellName,list);
        }
        catch (Exception e){
            throw  new Exception("导出发生异常"+e);
        }



    }
}
