package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_oldgroup")
public class OldGroupVo  implements Serializable {

    private static final long serialVersionUID=1L;

    private String name;

    private String area;

    private String major;

    private String type;

}
