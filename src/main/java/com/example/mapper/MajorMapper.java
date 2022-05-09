package com.example.mapper;

import com.example.vo.CourseVo;
import com.example.vo.MajorVo;
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
public interface MajorMapper extends BaseMapper<MajorVo> {
    /**
     * @param name 模糊查询的前边的词
     * @return
     */
    public List<MajorVo> fuzzySearch(String name);


    public void deleteAll();
}
