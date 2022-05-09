package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.service.AuthService;
import com.example.service.UserService;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
     @Autowired
     UserService userService;
     @Autowired
     UserMapper userMapper;



    @Override
    public UserVo findByUserId(String userId) {

        UserVo userVo = userMapper.findByUsername(userId);
        return userVo;

    }

    //12小时后失效
    private final static int EXPIRE = 12;

    @Override
    public String createToken(UserVo user) {

        //用UUID生成token
        String token = UUID.randomUUID().toString();
        //当前时间
        LocalDateTime now = LocalDateTime.now();
        //过期时间
        LocalDateTime expireTime = now.plusHours(EXPIRE);
        //保存到数据库
        user.setLoginTime(now);
        user.setExpireTime(expireTime);
        user.setToken(token);
        userService.saveOrUpdate(user);
        return token;
    }

    @Override
    public void logout(String token) {
        UserVo userVo = userMapper.findByToken(token);
        //用UUID生成token
        token = UUID.randomUUID().toString();
        //修改用户的token使原本的token失效，前端需配合将token清除
        userVo.setToken(token);
        userService.saveOrUpdate(userVo);

    }

    @Override
    public UserVo findByToken(String token) {
        return userMapper.findByToken(token);
    }

}
