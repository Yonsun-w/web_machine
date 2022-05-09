package com.example.service.impl;

import com.example.utils.ExcelUtil;
import com.example.vo.MajorVo;
import com.example.mapper.MajorMapper;
import com.example.service.MajorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class MajorServiceImpl extends ServiceImpl<MajorMapper, MajorVo> implements MajorService {
@Autowired
MajorMapper majorMapper;
@Autowired
MajorService majorService;
    @Override
    public boolean batchImport(String fileName, MultipartFile file,String type) throws Exception {
        List<Object[]> Lists = ExcelUtil.importExcel(fileName,file);
        for(int i = 0;i<Lists.size();i++){
            Object[] arr = Lists.get(i);

            String academic_belong = arr[1]+ "";
            if(academic_belong==null||academic_belong.equals("")){
                throw new Exception("第"+(i+1)+"行发生错误");
            }

            String major_class = arr[2]+"";
            if(major_class==null||major_class.equals("")){
                throw new Exception("第"+(i+1)+"行发生错误");
            }
            //专业代码
            String id = arr[3]+"";
            if(id==null||id.equals("")){
                throw new Exception("第"+(i+1)+"行 专业代码 发生错误");
            }
            //专业名称
            String name = arr[4] + "";
            if(name==null||name.equals("")){
                throw new Exception("第"+(i+1)+"行 专业名称 发生错误");
            }
            //所授学位
            String academic = arr[5] + "";
            if(academic==null||academic.equals("")){
                throw new Exception("第"+(i+1)+"行 学位授予 发生错误");
            }


            MajorVo majorVo = new MajorVo();
            majorVo.setAcademicBelong(academic_belong);
            majorVo.setMajorClass(major_class);
            majorVo.setId(id);
            majorVo.setName(name);
            majorVo.setAcademic(academic);
            majorVo.setType(type);
            try {

              majorService.saveOrUpdate(majorVo);
            }
            catch (Exception e){
              throw new Exception("发生错误了："+e);
            }



        }


        return false;
    }

    @Override
    public List<MajorVo> fuzzySeach(String name) {
        try {
            name +="%";
            return     majorMapper.fuzzySearch(name);
        }
        catch (Exception e){
            System.out.println("课程模糊查询发生错误"+e);
            return null;
        }
    }
}
