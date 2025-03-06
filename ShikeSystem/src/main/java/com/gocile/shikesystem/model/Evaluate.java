package com.gocile.shikesystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 用在需要两种评价混着处理的场景
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {
    //评价内容
    private String content;
    //是否已经被审核（被审核不等于通过审核），0表示否，1表示是
    private int audited;
    //是否被通过，只有审核了并且通过了才会变成是（1）
    private int passed;
}
