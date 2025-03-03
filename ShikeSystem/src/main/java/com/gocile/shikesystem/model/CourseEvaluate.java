package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEvaluate {
    private int id;
    private String content;
    @TableField(value = "stu_id")
    private String stuId;
    @TableField(value = "course_id")
    private String courseId;
    private int audited;
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
