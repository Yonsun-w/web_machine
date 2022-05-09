package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.BookVo;
import com.example.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface AuthService  {

    /**
     * 根据用户名查找用户
     * @param userId
     * @return
     */
    UserVo findByUserId(String userId);

    /**
     * 为user生成token
     * @param user
     * @return
     */
    String createToken(UserVo user);

    /**
     * 根据token去修改用户token ，使原本token失效
     * @param token
     */
    void logout(String token);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    UserVo findByToken(String token);
}
