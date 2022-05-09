package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.BookVo;
import com.example.vo.MajorVo;
import com.example.vo.ProjectVo;
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
public interface ProjectService extends IService<ProjectVo> {
    /**
     * 给书籍进行默认分组
     * @Prama size 每组大小
     * @return boolean 说明是否成功
     */
    boolean groupBook(int size) throws Exception;

    /**
     * 给课程进行默认分组
     * @Prama size 每组大小
     * @return boolean 说明是否成功
     */
    boolean groupCourse(int size) throws Exception;

    /**
     * 撤销所有id在list的分组分组
     *
     * @param Ids
     * @return boolean 说明是否成功
     * @throws Exception
     */
    boolean Ungroup(List<String> Ids) throws Exception;

    /**
     * 给所有书籍自动打领域 如果没有就让手动打
     */
    boolean groupAreaBook() throws Exception;



    /**
     * 给课程自动打领域 如果没有就让手动打
     */
    boolean groupAreaCourse() throws Exception;


    /**
     * 给所有项目分配专家
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    boolean allGroupExpert(int center,int local) throws  Exception;



    /**按领域类分配专家
     * 给一个项目分配专家分配专家
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    boolean  GroupExpert(String Id,int center,int local ) throws  Exception;

    /**  回避
     * 按照领域分类给一个项目
     * @param Id
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    boolean  GroupExpertAvoid(String Id,int center,int local ) throws  Exception;

    /**
     * 给项目设置回避
     * @param Ids
     * @return
     * @throws Exception
     */
    boolean  setAvoid(List<String> Ids) throws  Exception;

    /**
     * 给项目撤销回避
     * @param Ids
     * @return
     * @throws Exception
     */
    boolean  resetAvoid(List<String> Ids) throws  Exception;


    /**
     * 给项目设专家比例
     * @param Ids   项目id
     * @param center   中央专家人数 -1代表不要求
     * @param local    非中央专家人数
     * @return
     * @throws Exception
     */
    boolean  setExpert(List<String> Ids,int center,int local) throws  Exception;




    /**
     * 按专业大类分配专家
     * 给一个项目分配专家分配专家
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    boolean  MajorGroupExpert(String id,int center,int local ) throws  Exception;



    /**
     * 回避
     * 按专业大类分配专家
     * 给一个项目分配专家分配专家
     * @param id
     * @param center
     * @param local
     * @return
     * @throws Exception
     */
    boolean  MajorGroupExpertAovid(String id,int center,int local ) throws  Exception;


    /**
     * 返回格式
     * list
     *  list 里的一条元素为 ：项目号  书籍List 课程List 专家list
     * @return
     */
    List   listAll() throws  Exception;

    /**
     * 导出所有书籍分组信息
     * 导出格式为 书名 组号
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void exportBook(HttpServletResponse response, String fileName) throws Exception;

    /**
     * 导出所有课程分组信息
     * 导出格式为 id 课程名 组号
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void exportCourse(HttpServletResponse response, String fileName) throws Exception;


    /**
     * 导出所有专家分组信息
     * 导出格式为 手机号 专家名 组号
     * @param response http响应
     * @param fileName 下载的文件名
     * @throws Exception
     */
    public void exportExpert(HttpServletResponse response, String fileName) throws Exception;







    /**
     * 批量导入图书
     * @param fileName
     * @param file
     * @return boolean 说明是否成功
     * @throws Exception
     */
    boolean batchImportBook(String fileName, MultipartFile file) throws Exception;



    /**
     * 批量导入课程
     * @param fileName
     * @param file
     * @return boolean 说明是否成功
     * @throws Exception
     */
    boolean batchImportCourse(String fileName, MultipartFile file) throws Exception;

    /**
     * 批量导入课程
     * @param fileName
     * @param file
     * @return boolean 说明是否成功
     * @throws Exception
     */
    boolean batchImportExpert(String fileName, MultipartFile file) throws Exception;





}