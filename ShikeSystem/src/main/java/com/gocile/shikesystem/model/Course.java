package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @TableId
    private String id;
    private String title;
    private int grade;
    private String college;
    private String major;
    private int capacity;
    private String optionality;
    private String category;
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
