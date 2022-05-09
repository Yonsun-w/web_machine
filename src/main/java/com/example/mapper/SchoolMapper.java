package com.example.mapper;

import com.example.vo.CourseVo;
import com.example.vo.SchoolVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
public interface SchoolMapper extends BaseMapper<SchoolVo> {

    /**
     * 通过学校名找
     */
    public SchoolVo selectByName(String name);

    /**
     * @param name 模糊查询的前边的词
     * @return
     */
    public List<SchoolVo> fuzzySearch(String name);
}
