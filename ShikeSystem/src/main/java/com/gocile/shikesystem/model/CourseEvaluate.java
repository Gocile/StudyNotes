package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 针对课程评价的数据模型
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEvaluate {
    //评价的id
    private int id;
    //评价内容
    private String content;
    @TableField(value = "stu_id")
    private String stuId;
    @TableField(value = "course_id")
    private String courseId;
    //是否已经被审核（被审核不等于通过审核），0表示否，1表示是
    private int audited;
    //是否被通过，只有审核了并且通过了才会变成是（1）
    private int passed;

    public CourseEvaluate(int id, int audited, int passed) {
        this.id = id;
        this.audited = audited;
        this.passed = passed;
    }

    public CourseEvaluate(String courseId, String stuId, String content) {
        this.courseId = courseId;
        this.stuId = stuId;
        this.content = content;
    }
}
