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
@TableName("t_book")

public class BookVo implements Serializable {


    private String name;

    private String unit;

    private String author_major1;

    private String authorUnit;

    private String author;

    private String province;

    private String type;

    private String copies;

    private String language;

    private String student;

    private String major;

    private String publisher;

    private String version;

    private String area;

    private String Rarea;

    private String flag;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private static final long serialVersionUID=1L;






}
