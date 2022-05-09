package com.example.service;

import com.example.vo.CourseVo;
import com.example.vo.MajorVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
public interface MajorService extends IService<MajorVo> {
    /**
     * 批量导入数据
     * @return boolean 说明是否成功
     */
    boolean batchImport(String fileName, MultipartFile file,String type) throws Exception;

    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<MajorVo> fuzzySeach(String name);
}
