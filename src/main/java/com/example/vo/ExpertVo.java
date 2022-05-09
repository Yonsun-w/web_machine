package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Primary;

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
@TableName("t_expert")
public class ExpertVo implements Serializable {



      private String name;

      private String flag;

      private String school;

      private String province;

    private String school_type;

    private String job1;

    private String unit;

    private String job2;

    private String major1;

    private String course1;

    private String major2;

    private String course2;

    private String major3;

    private String course3;

      @TableId
      private String phone;

      private String birth;

    private String role;

    private String sex;

    private String politic;

    private String nation;

    private String email;

    private String pwd;


    private static final long serialVersionUID=1L;
















}
