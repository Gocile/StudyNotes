package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 针对管理员的评价的数据模型
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEvaluate {
    //评价的id
    private int id;
    //评价内容
    private String content;
    @TableField(value = "stu_id")
    private String stuId;
    @TableField(value = "admin_id")
    private String adminId;
    //是否已经被审核（被审核不等于通过审核），0表示否，1表示是
    private int audited;
    //是否被通过，只有审核了并且通过了才会变成是（1）
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
