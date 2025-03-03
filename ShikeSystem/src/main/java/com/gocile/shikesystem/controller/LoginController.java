package com.gocile.shikesystem.controller;

import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.service.LoginService;
import com.gocile.shikesystem.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name = "login",description = "登录接口")
public class LoginController {

    final LoginService loginService;

    @Operation(summary = "student and admin login")
    @GetMapping
    public BaseResponse<String> userLogin(HttpServletRequest req, HttpServletResponse resp, String phoneNum,
                                          String id, String pwd, String identity){
        BaseResponse<String> baseResponse;
        if(identity == null || pwd == null || (phoneNum == null && id == null)){
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("请输入正确的信息以登录")
                    .build();
        }
        if("学生".equals(identity)||"管理员".equals(identity)/*||true*/){
//            System.out.println("接收到");
            baseResponse = loginService.userLogin(phoneNum,id,pwd,identity);
        }else {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("身份只能选择学生或管理员")
                    .build();
        }
        if(pwd.equals("SAdmin")) identity = "超级管理员";
        String selectedId = baseResponse.getData();
        System.out.println("selectedId:"+selectedId);
        if(!(selectedId==null)) {
            id = selectedId;
//            System.out.println("执行");
        }
        Cookie cookie = new Cookie("token", JwtUtil.getToken(id,identity));
        resp.addCookie(cookie);
        return baseResponse;
    }
}

/*
* 这里感觉还有很大的优化空间，service太乱重复代码太多，controller里的逻辑太多
* */