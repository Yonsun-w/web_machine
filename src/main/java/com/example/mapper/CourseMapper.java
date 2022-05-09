package com.example.mapper;

import com.example.vo.BookVo;
import com.example.vo.CourseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.PoJoProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Mapper
public interface CourseMapper extends BaseMapper<CourseVo> {


    /**
     * @param name 模糊查询的前边的词
     * @return
     */
    public List<CourseVo> fuzzySearch(@Param("name")String name);
    /**
     * 返回组号为flag的教材
     * @param flag
     * @return
     */
    public List<CourseVo> listByIdAll(@Param("flag")String flag);


    /**
     * @return 获取所有未分组项目的  所有种类
     */
    public List<Map>  getUnGroupArea();

    /**
     * @param area
     * @return 返回该领域出现最多的专业类
     * @throws Exception
     */
    String areaGetMajor(@Param("area") String area)throws Exception;




    /**
     * @param area 领域
     * @return 返回领域为area所有没有分组的书
     */
    List<CourseVo> selectNoarea(@Param("area") String area);

    /**
     * 删除所有教材
     * @return
     */
    public  void deleteAll() throws Exception;


    /**
     * 删除所有教材
     * @return
     */
    public  void deleteGroup(@Param("flag") String flag) throws Exception;


    /**
     * 返回所有没有打领域的
     */
    List<CourseVo> selectAllNoarea();


    /**
     * 返回课程 id 名称、组号 信息
     * @return
     * @throws Exception
     */
    List<PoJoProjectVo> export() throws Exception;
}
