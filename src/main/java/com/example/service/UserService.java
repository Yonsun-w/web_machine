package com.example.service;

import com.example.vo.SchoolVo;
import com.example.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
public interface UserService extends IService<UserVo> {
    /**
     通过token取User
     */
    UserVo findeByToken(String token);

}
