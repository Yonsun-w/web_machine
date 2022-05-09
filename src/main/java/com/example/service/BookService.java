package com.example.service;

import com.example.vo.BookVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.CourseVo;
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
public interface BookService extends IService<BookVo> {
    /**
     * 批量导入图书
     * @return boolean 说明是否成功
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;



    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<BookVo> fuzzySeach(String name);

    /**
     * 导出所有书籍的信息(按照申报格式)
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void export(HttpServletResponse response,String fileName) throws Exception;
}
