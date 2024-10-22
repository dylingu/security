package com.lingu.mp.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField("username")
    private String name;

    private String password;

    private String role;

}
