package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 建立学生和课程之间的映射关系
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuCourse {
    @TableField(value = "stu_id")
    private String stuId;
    @TableField(value = "course_id")
    private String courseId;
    @TableLogic
    private int status;
}
