package com.example.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * 书籍或者教材和组号对应关系
 */
@Data
public class PoJoProjectVo{
    //主键
    private String id;

    //名称
    private String name;

    //组号
    private String flag;

}
