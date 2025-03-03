package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* @Author  Gocile
* @Date  2025/2/21
* @Description  作为学生的数据模型
* */
@Data
@NoArgsConstructor
public class Stu {
    @TableId
    private String id;
    @TableField(value = "stu_password")
    private String pwd;
    @TableField(value = "stu_name")
    private String name;
    private String gender;
    @TableField("phone_num")
    private String phoneNum;
    private String email;
    private String college;
    private String major;
    private int grade;
    @TableField("major_quantity")
    private int majorQuantity;
    @TableField("pe_quantity")
    private int peQuantity;
    @TableField("culture_quantity")
    private int cultureQuantity;

    public Stu(String id,String email,String phoneNum,String pwd){
        this.id = id;
        this.email = email;
        this.phoneNum = phoneNum;
        this.pwd = pwd;
    }

    public Stu(String id, String pwd, String name, String gender, String phoneNum, String email, String college, String major, int grade, int majorQuantity, int peQuantity, int cultureQuantity) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.college = college;
        this.major = major;
        this.grade = grade;
        this.majorQuantity = majorQuantity;
        this.peQuantity = peQuantity;
        this.cultureQuantity = cultureQuantity;
    }

    public Stu(String id, String name, String gender, String phoneNum, String email, String college, String major, int grade) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.college = college;
        this.major = major;
        this.grade = grade;
    }

    public Stu(String id, String pwd, String name, String gender, String phoneNum, String email, String college, String major, int grade) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.email = email;
        this.college = college;
        this.major = major;
        this.grade = grade;
    }
}