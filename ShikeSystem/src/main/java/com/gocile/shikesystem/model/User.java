package com.gocile.shikesystem.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 作为JwtUtil和filter之间传输数据（用户的权限和id）的模型
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private String permission;
    @TableId
    private String id;
}