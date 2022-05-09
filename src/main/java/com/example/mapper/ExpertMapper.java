package com.example.mapper;

import com.example.vo.CourseVo;
import com.example.vo.ExpertVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.PoJoProjectVo;
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
public interface ExpertMapper extends BaseMapper<ExpertVo> {


    /**
     * 删除所有专家
     */
    void deleteAll();

    /**
     * @param name 模糊查询的前边的词
     * @return
     */
    List<ExpertVo> fuzzySearch(
            @Param("name") String name);



    /**
     * @param flag  返回flag(也就是组号为flag的所有专家)
     * @return
     */
    List<ExpertVo> listByFlag(
            @Param("flag") String flag);


    /**
     * 撤销分组为 id 的专家分配
     * @param flag
     * @return
     */
    boolean deleteAllgroup(@Param("flag") String flag);


    /**
     * 返回所有没有密码的专家
     * @return
     */
    List<ExpertVo> listNoPwd();



    /**
     * 通过专业查找专家集合
     * @param major
     * @param size limt大小 查找几个
     * @param type
     * @return
     */
    List<ExpertVo> selectBymajor(@Param("major") String major
            , @Param("size")int size, @Param("type")String type);

    /**
     * 通过专业查找非中央专家集合
     * @param major
     * @param size limt大小 查找几个
     * @return
     */
    List<ExpertVo> selectLocalBymajor(@Param("major") String major
            , @Param("size")int size);


    /**设置回避
     * 通过专业查找中央高校专家集合
     * @param major
     * @param size limt大小 查找几个
     * @param type
     * @param id：回避项目的id
     * @return
     */
    List<ExpertVo> selectBymajorAvoid(@Param("major") String major,
                                  @Param("size")int size,
                                 @Param("type")String type,
                                 @Param("id")String id);


    /**设置回避
     * 通过专业查找非中央专家集合
     * @param major
     * @param size limt大小 查找几个
     * @return
     */
    List<ExpertVo> selectLocalBymajorAvoid(@Param("major") String major
                                         , @Param("size")int size,
                                           @Param("id")String id);






    /**
     * 通过领域查找专家集合
     * @param area 领域
     * @param size limt大小 查找几个
     * @return
     */
    List<ExpertVo> selectByarea(@Param("area") String area
                              , @Param("size")int size,
                                @Param("type")String type);

    /**
     * 通过领域查找非中央专家集合
     * @param area 领域
     * @param size limt大小 查找几个
     * @return
     */
    List<ExpertVo> selectLocalByArea(@Param("area") String area
            , @Param("size")int size);






    /**设置回避
     * 通过领域查找中央高校专家集合
     * @param area
     * @param size limt大小 查找几个
     * @param type
     * @param id：回避项目的id
     * @return
     */
    List<ExpertVo> selectByAreaAvoid(@Param("area") String area,
                                      @Param("size")int size,
                                      @Param("type")String type,
                                      @Param("id")String id);


    /**设置回避
     * 通过领域查找非中央专家集合
     * @param area
     * @param size limt大小 查找几个
     * @param id
     * @return
     */
    List<ExpertVo> selectLocalByAreaAvoid(@Param("area") String area
                                         , @Param("size")int size,
                                           @Param("id")String id);


    /**
     * 返回  手机 姓名 组号 信息
     * @return
     * @throws Exception
     */
    List<PoJoProjectVo> export() throws Exception;
}
