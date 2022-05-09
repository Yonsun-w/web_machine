package com.example.mapper;

import com.example.vo.BookVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.CourseVo;
import com.example.vo.PoJoProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface BookMapper extends BaseMapper<BookVo> {


   /**
    * 根据名字 作者 单位 查找书籍
    * @param name
    * @param author
    * @param unit
    * @return
    */
   BookVo selectByName(String name,String author,String unit);
   /**
    * 返回所有未分组的书
    */
   List<BookVo> selectAllUngroup();

   /**
    * 返回所有已经分组的书
    */
   List<BookVo> selectAllgroup();



   /**
    * 返回所有分组后组号为n的书
    */
   List<BookVo> selectAllIdgroup(@Param("flag") String flag);




   /**
    * 撤销所有 主键为id 的书的分组
    * @param flag
    * @return
    */
   boolean deleteAllIdgroup(@Param("flag") String flag);

   /**
    * 撤销所有 书的分组
    */
   boolean deleteAllgroup();




   /**
    * 返回所有没有打领域的
    */
   List<BookVo> selectAllNoarea();




   /**
    * @param name 模糊查询的前边的词
    * @return
    */
   public List<BookVo> fuzzySearch(String name);


   /**
    * @return 获取所有未分组项目的的 所有种类
    */
   public List<Map>  getUnGroupArea();


   /**
    * @param area 领域
    * @return 返回领域为area 并且没有分组的 所有书
    */
   List<BookVo> selectNoarea(String area);

   /**
    * 删除所有数据库教材
    */
 void deleteAll() throws Exception;

   /**
    * @param area
    * @return 返回该领域出现最多的专业类
    * @throws Exception
    */
 String areaGetMajor(@Param("area") String area)throws Exception;


   /**
    * 返回书籍 id 名称、组号 信息
    * @return
    * @throws Exception
    */
   List<PoJoProjectVo> export() throws Exception;


}
