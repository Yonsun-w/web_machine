package com.example.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("t_project")
public class ProjectVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String id;

    private String area;

    private String type;

    private String major;

    private String flag;

    private Integer center;

    private Integer local;

    private String avoid;

    private Integer expert;

    private Integer needLocal;

    private Integer needCenter;

    private Integer needExpert;




}
