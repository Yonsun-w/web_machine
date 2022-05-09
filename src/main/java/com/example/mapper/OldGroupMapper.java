package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.vo.OldGroupVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 旧的知识库
 */
@Mapper
public interface OldGroupMapper extends BaseMapper<OldGroupVo> {
    /**
     *对旧的分组领域模糊查询。
     *返回 type类型 教材/课程
     * 领域word的条数
     */

    @Select(value = "select major,area, count(area) as count from t_oldgroup where name like  CONCAT('%',#{area},'%')" +
            "and type like CONCAT('%',#{type},'%') group by area order by " +
            "count(area) desc;")
    List<Map> fuzzy_search(@Param(value="area")String area,
                                 @Param(value="type")String type);

    /**
     * 根据名字查询知识库
     */
    @Select(value = "select * from t_oldgroup where name like  CONCAT('%',#{name},'%') limit 0,1")
    OldGroupVo selectByName(@Param(value = "name") String name);

}
