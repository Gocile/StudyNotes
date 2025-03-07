package com.gocile.shikesystem.advice;

import com.gocile.shikesystem.exception.NotLoggedInException;
import com.gocile.shikesystem.exception.OperationException;
import com.gocile.shikesystem.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 处理请求参数错误异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400，通用客户端请求错误，如参数错误
    public BaseResponse<String> handleRequestException(MethodArgumentNotValidException e) {
        return BaseResponse.<String>builder()
                .success(false)
                .msg("参数错误，请重试")
                .build();
    }

    // 处理未登录异常
    @ExceptionHandler(NotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)//401，未认证
    public BaseResponse<String> handleNotLoggerInException(NotLoggedInException e) {
        return BaseResponse.<String>builder()
                .success(false)
                .msg(e.getMessage())
                .build();
    }

    // 处理请求方法错误异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)//405，请求方法不支持
    public BaseResponse<String> handleWrongMethodException(HttpRequestMethodNotSupportedException e) {
        return BaseResponse.<String>builder()
                .success(false)
                .msg("请求错误，请稍后再试")
                .build();
    }

    //处理一般错误
    @ExceptionHandler(OperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400，请求出错
    public BaseResponse<String> handleBusinessException(OperationException e) {
        return BaseResponse.<String>builder()
                .success(false)
                .msg(e.getMsg())
                .build();
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500，服务器内部错误
    public BaseResponse<String> handleOtherException(Exception e) {
        return BaseResponse.<String>builder()
                .success(false)
                .msg("系统繁忙，请稍后再试")
                .build();
    }
}
