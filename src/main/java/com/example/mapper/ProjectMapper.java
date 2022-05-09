package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.MajorVo;
import com.example.vo.ProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Mapper
public interface ProjectMapper extends BaseMapper<ProjectVo> {

    /**
     * @param name
     * @return
     */
    ProjectVo selectByName(String name);

    /**
     * @return 删除所有书籍分组
     */
    boolean deleteAllbook();

    /**
     * @return 删除所有课程分组
     */
    boolean deleteAllcourse();

    /**
     * @return 获取最大id
     */
    String getMaxId();


    /**
     * @param type 教材/课程
     * @return 返回未分配好专家的项目
     */
    List<ProjectVo> selectNoExpert(@Param("type") String type);


    /**
     * @throws Exception
     */
    void Update() throws  Exception;


    /**
     * @return 返回需要回避专家分配的所有未分组类型
     */
    List<ProjectVo> getAvoid(@Param("type") String type);



    /**
     * @return 返回不需要回避专家分配的所有未分组类型
     */
    List<ProjectVo> getNoAvoid(@Param("type") String type);



}
