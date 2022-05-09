package com.example.vo;

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
@TableName("t_school")
public class SchoolVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String name;

    private String id;


    private String province;

    private String city;

    private String type;

    private String belong_name;

    private String layer;

    private String school_layer;

    private String belong;

    private String dd;

}
