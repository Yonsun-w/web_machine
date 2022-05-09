package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UnitMapper;
import com.example.service.UnitService;
import com.example.vo.UnitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, UnitVo> implements UnitService {
    @Autowired
    UnitMapper unitMapper;

}
