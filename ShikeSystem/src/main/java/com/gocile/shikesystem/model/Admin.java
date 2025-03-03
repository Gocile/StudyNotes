package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author  Gocile
 * @Date  2025/2/21
 * @Description  作为管理员的数据模型
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @TableId
    private String id;
    @TableField(value = "admin_password")
    private String pwd;
    @TableField(value = "admin_name")
    private String name;
    private String gender;
    @TableField(value = "phone_num")
    private String phoneNum;
    private String email;
    private String college;

    public Admin(String id,String email,String phoneNum,String pwd){
        this.id = id;
        this.email = email;
        this.phoneNum = phoneNum;
        this.pwd = pwd;
    }

    public Admin(String id, String name, String gender, String phoneNum, String email, String college) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.college = college;
    }
}
