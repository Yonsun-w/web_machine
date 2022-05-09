package com.example.service;

import com.example.vo.CourseVo;
import com.example.vo.SchoolVo;
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
public interface SchoolService extends IService<SchoolVo> {
    /**
     * 批量导入高校
     * @return boolean 说明是否成功
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<SchoolVo> fuzzySeach(String name);
}
