package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEvaluate {
    private int id;
    private String content;
    @TableField(value = "stu_id")
    private String stuId;
    @TableField(value = "admin_id")
    private String adminId;
    private int audited;
    private int passed;

    public AdminEvaluate(String adminId, String stuId, String content) {
        this.adminId = adminId;
        this.stuId = stuId;
        this.content = content;
    }

    public AdminEvaluate(int id, int audited, int passed) {
        this.id = id;
        this.audited = audited;
        this.passed = passed;
    }
}
