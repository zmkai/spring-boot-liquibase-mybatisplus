package com.hand.demospringbootliqubasemybatispuls.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("department")
public class Department {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @TableField(value = "name")
    private String name;

    private boolean active;
}
