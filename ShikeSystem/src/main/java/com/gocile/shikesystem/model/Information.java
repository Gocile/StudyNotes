package com.gocile.shikesystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/*
 * @Author Gocile
 * @Description 作为information表的数据模型
 * Tip：该模型表示的是面向某群学生（最小为一个专业，最大为全部）的某个课程的选课信息
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Information {
    //面向年级
    private int grade;
    //面向学院
    private String college;
    //面向专业
    private String major;
    //课程的种类（专业课程/体育/素质课程）
    private String category;
    //选课开始时间
    private LocalDateTime selectTime;
    //允许的最大选课数量
    private int maxQuantity;
}
