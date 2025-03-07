package com.gocile.shikesystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
/*
 * @Author Gocile
 * @Description 发生一般异常时抛出此异常
 * */
@Data
@AllArgsConstructor
public class OperationException extends RuntimeException {
    private String msg;
}
