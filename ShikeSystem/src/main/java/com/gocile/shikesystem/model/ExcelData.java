package com.gocile.shikesystem.model;

import lombok.Data;
/*
 * @Author Gocile
 * @Description 因为超级管理员上传的excel中有学生和管理员的信息，所以先用这个类承接，再根据identity属性分类存储
 * */
@Data
public class ExcelData {
    //身份标识，1为学生，2为老师
    private int identity;
    private String name;
    private String gender;
    private String id;
    private String phoneNum;
    private String email;
    private String college;
    //以下两个属性只有学生有，管理员的为空
    private String major;
    private int grade;
}
