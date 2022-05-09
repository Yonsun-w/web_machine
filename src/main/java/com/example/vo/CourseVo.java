package com.example.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wjh
 * @since 2021-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_course")

public class CourseVo implements Serializable {
    private String type;

    private String year;

    private String name;



    private String school;

    private String admin;

    private String province;

    private String majorClass;

    private String plat;

    private String Cclass;

    private String Ctype;

    private String peopleMajor;

    private String people;

    private String area;

    private String Rarea;

    private String flag;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID=1L;


}
