package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.vo.OldGroupVo;
import org.springframework.web.multipart.MultipartFile;

public interface OldGroupService extends IService<OldGroupVo> {

    /**
     * 批量导入往年书本分组
     * @return boolean 说明是否成功
     */
    boolean BookbatchImport(String fileName, MultipartFile file) throws Exception;



    /**
     * 批量导入往年课程分组
     * @return boolean 说明是否成功
     */
    boolean CoursebatchImport(String fileName, MultipartFile file) throws Exception;

}
