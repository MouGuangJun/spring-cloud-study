package com.compose.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author gjmou
 * @since 2022-07-02
 */
@Getter
@Setter
@TableName("reader")
public class Reader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("card_id")
    private String cardId;

    @TableField("name")
    private String name;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private Integer age;

    @TableField("tel")
    private String tel;

    @TableField("balance")
    private BigDecimal balance;

    @TableField("inputdate")
    private String inputdate;

    @TableField("updatedate")
    private String updatedate;

    @TableField("remark")
    private String remark;


}
