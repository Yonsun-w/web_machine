package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.PipedReader;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_unit_expert")
public class UnitExpertVo  implements Serializable {



    @TableId
    private String id;

    private String unit_name;

    private String expert_name;

    private static final long serialVersionUID=1L;

}
