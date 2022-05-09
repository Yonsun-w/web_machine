package com.example.service;

import com.example.vo.CourseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
public interface CourseService extends IService<CourseVo> {


    /**
     * 批量导入课程
     * @return boolean 说明是否成功
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;


    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<CourseVo> fuzzySeach(String name);

    /**
     * 导出所有课程的信息(按照申报格式)
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void export(HttpServletResponse response, String fileName) throws Exception;
}
