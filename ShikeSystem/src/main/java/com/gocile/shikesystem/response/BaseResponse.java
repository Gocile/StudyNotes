package com.gocile.shikesystem.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * @Author Gocile
 * @Description 统一返回类
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private String msg;
    private Boolean success;
    private T data;

    public BaseResponse<T> construct(String msg, Boolean success){
        this.msg = msg;
        this.success = success;
        return this;
    }

    public BaseResponse<T> construct(String msg, Boolean success, T data){
        this.msg = msg;
        this.success = success;
        this.data = data;
        return this;
    }
}