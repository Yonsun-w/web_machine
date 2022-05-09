package com.example.service.impl;

import com.example.utils.ExcelUtil;
import com.example.vo.ExpertVo;
import com.example.vo.SchoolVo;
import com.example.mapper.SchoolMapper;
import com.example.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, SchoolVo> implements SchoolService {
    @Autowired
    SchoolMapper schoolMapper;
    @Autowired
    SchoolService schoolService;

    /**
     * 批量导入数据
     * @param fileName
     * @param file
     * @return 是否成功
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        List<Object[]> list =   ExcelUtil.importExcel(fileName,file);
        //由于军队院校没有标识符，默认用 0 1 2 3作为标识符
        int arm_id = 1;

        //依次取出所有数据
        for(int i = 0;i<list.size();i++){
            //依次取出每行
           Object[] tmp_Object = list.get(i);

           String name = "",id = "" , province = "",
                   belong_name = "",city = "",
                   school_layer = "",layer = "",type = "公办",dd = "",belong = "";

           if(tmp_Object[1]==null||tmp_Object[3]==null
           ||tmp_Object[4]==null||tmp_Object[5]==null||tmp_Object[7]==null||
           tmp_Object[9]==null){
               throw new Exception("第"+(i+1)+"行数据发生错误，请仔细校队该行数据格式");
           }


           name = tmp_Object[1]+"";
           id = tmp_Object[2]+"";
           belong_name = tmp_Object[3]+"";
           city = tmp_Object[4]+"";
           layer = tmp_Object[5]+"";
           province = tmp_Object[7]+"";
            school_layer = tmp_Object[9]+"";

            //如果是军校 默认为 1 2 3 id
            if(id.equals("")){
                id = arm_id+"";
                arm_id++;
            }


            //如果备注民办
            if(tmp_Object[6]!=null){
                type = tmp_Object[6]+"";
            }

            //如果部署单位存在
            if(tmp_Object[8]!=null){
                belong_name = tmp_Object[8]+"";
            }

            //如果有双一流
            if(tmp_Object[10]!=null){
                dd = tmp_Object[10]+"";
            }

            SchoolVo schoolVo = schoolMapper.selectByName(name);
            if(schoolVo!=null){
                schoolService.removeById(schoolVo);
            }

            schoolVo = new SchoolVo();
            schoolVo.setId(id);
            schoolVo.setName(name);
            schoolVo.setProvince(province);
            schoolVo.setCity(city);
            schoolVo.setType(type);
            schoolVo.setBelong_name(belong_name);
            schoolVo.setBelong(belong);
            schoolVo.setDd(dd);
            schoolVo.setLayer(layer);
            schoolVo.setSchool_layer(school_layer);

            try{
             schoolService.saveOrUpdate(schoolVo);
            }
            catch (Exception e){
                throw  new Exception("更新失败，第"+(i+1)+"行未知原因失败，控制台打印"+e);
            }
            System.out.println(name + "成功");


        }//for end

        return true;
    }

    @Override
    public List<SchoolVo> fuzzySeach(String name) {
        try {
            name +="%";
            return     schoolMapper.fuzzySearch(name);
        }
        catch (Exception e){
            System.out.println("课程模糊查询发生错误"+e);
            return null;
        }
    }


}
