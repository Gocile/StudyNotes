package com.gocile.shikesystem.exception;

import lombok.Data;

/*
* @Author Gocile
* @Description 用户未登录或者token过期时抛出此异常
* */
@Data
public class NotLoggedInException extends RuntimeException{
    //异常提示信息
    private static String msg = "未登录或登录过期，请重新登录";
}
