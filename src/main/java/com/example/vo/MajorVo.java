package com.example.vo;

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
@TableName("t_major")
public class MajorVo implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private String name;

    private String type;

    private String academic;

    private String academicBelong;

    private String majorClass;

    private Integer time;


}
