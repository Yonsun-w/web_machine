package com.example.service.impl;

import com.example.service.AuthService;
import com.example.vo.ExpertVo;
import com.example.vo.UserVo;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author wjh
 * @since 2021-10-15
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserVo> implements UserService {


    @Override
    public UserVo findeByToken(String token) {
        return null;
    }
}
