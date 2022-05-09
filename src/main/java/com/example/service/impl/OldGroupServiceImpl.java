package com.example.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.OldGroupMapper;
import com.example.service.OldGroupService;
import com.example.utils.ExcelUtil;
import com.example.vo.OldGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Slf4j
@Service
public class OldGroupServiceImpl extends ServiceImpl<OldGroupMapper, OldGroupVo> implements OldGroupService {
@Autowired

OldGroupMapper oldGroupseMapper;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean BookbatchImport(String fileName, MultipartFile file) throws Exception {
        List<Object[]> list = ExcelUtil.importExcel(fileName,file);
        for(int i = 0;i<list.size();i++){
            Object[] tmpObject = list.get(i);
            String name  = tmpObject[0]+"";
            String major = tmpObject[3]+"";
            String area = tmpObject[4]+"";
            if(StringUtils.isBlank(name) || StringUtils.isBlank(major) || StringUtils.isBlank(area)){
                throw new Exception((i+1)+"行错误，请查看");
            }
            OldGroupVo oldGroupVo = new OldGroupVo();

            oldGroupVo.setMajor(major);
            oldGroupVo.setArea(area);
            oldGroupVo.setName(name);
            oldGroupVo.setType("教材");
          try{
              oldGroupseMapper.insert(oldGroupVo);
          }catch (Exception e){
              throw  new Exception("错误，请联系管理员："+e);
          }

        }

        return false;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public boolean CoursebatchImport(String fileName, MultipartFile file) throws Exception {
        List<Object[]> list = ExcelUtil.importExcel(fileName,file);
        for(int i = 0;i<list.size();i++){
            Object[] tmpObject = list.get(i);
            String name  = tmpObject[0]+"";
            String major = tmpObject[1]+"";
            String area = tmpObject[3]+"";
            if(name==null||major==null||area==null){
                throw new Exception((i+1)+"行错误，请查看");
            }
            OldGroupVo oldGroupVo = new OldGroupVo();

            oldGroupVo.setMajor(major);
            oldGroupVo.setArea(area);
            oldGroupVo.setName(name);
            oldGroupVo.setType("课程");
            try{
                oldGroupseMapper.insert(oldGroupVo);
            }catch (Exception e){
                throw  new Exception("错误，请联系管理员："+e);
            }

        }

        return false;
    }





}
