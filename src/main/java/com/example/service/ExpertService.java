package com.example.service;

import com.example.vo.CourseVo;
import com.example.vo.ExpertVo;
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
public interface ExpertService extends IService<ExpertVo> {
    /**
     * 批量导入专家数据
     * @return boolean 说明是否成功
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    /**
     * 删除所有参数
     * @return boolean 说明是否删除成功
     */
    boolean deleteAll();
    /**
     * 模糊查询
     * @param name
     * @return
     */
    List<ExpertVo> fuzzySeach(String name);


    /**
     * 导出所有专家得的信息(按照申报格式)
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void export(HttpServletResponse response, String fileName) throws Exception;


    /**
     * 导出所有专家-密码
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void exportPwd(HttpServletResponse response, String fileName) throws Exception;


    /**
     * 给所有专家自动生成密码
     * @throws Exception
     */
    public void setPwd() throws Exception;

}
