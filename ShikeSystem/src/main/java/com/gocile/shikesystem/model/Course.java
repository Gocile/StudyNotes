package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 课程的数据模型
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId
    private String id;
    //课程名称
    private String title;
    //面向年级
    private int grade;
    //面向学院
    private String college;
    //面向专业
    private String major;
    //课程容量
    private int capacity;
    //课程性质，必修/可选
    private String optionality;
    //课程类别，专业课程/体育/素质课程
    private String category;
    //开课学期
    private String semester;
    @TableField(value = "admin_name")
    private String adminName;
    @TableField(value = "admin_id")
    private String adminId;
    @TableLogic
    private int status;

    public Course(String id, String title, int grade, String college, String major, int capacity, String optionality,
                  String category, String semester) {
        this.id = id;
        this.title = title;
        this.grade = grade;
        this.college = college;
        this.major = major;
        this.capacity = capacity;
        this.optionality = optionality;
        this.category = category;
        this.semester = semester;
    }
}
