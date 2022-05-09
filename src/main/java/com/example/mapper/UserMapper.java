package com.example.mapper;

import com.example.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Mapper
public interface UserMapper extends BaseMapper<UserVo> {
    UserVo findByUsername(String userId);

    UserVo findByToken(String token);
}
