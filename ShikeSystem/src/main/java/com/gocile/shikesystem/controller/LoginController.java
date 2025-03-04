package com.gocile.shikesystem.controller;

import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.service.LoginService;
import com.gocile.shikesystem.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "login",description = "统一登录接口")
public class LoginController {

    final LoginService loginService;

    @Operation(summary = "登录",description = "手机号和id二选一")
    @PostMapping
    public BaseResponse<String> userLogin(HttpServletRequest req, HttpServletResponse resp, String phoneNum,
                                          String id, String pwd, String identity){
        BaseResponse<String> baseResponse;
        if(identity == null || pwd == null || (phoneNum == null && id == null)){
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("请输入正确的信息以登录")
                    .build();
        }
        if("学生".equals(identity)||"管理员".equals(identity)){
            baseResponse = loginService.userLogin(phoneNum,id,pwd,identity);
        }else {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("身份只能选择学生或管理员")
                    .build();
        }
        if(pwd.equals("SAdmin")) identity = "超级管理员";
        String selectedId = baseResponse.getData();
        if(!(selectedId==null)) {
            id = selectedId;
        }
        Cookie cookie = new Cookie("token", JwtUtil.getToken(id,identity));
        resp.addCookie(cookie);
        return baseResponse;
    }
}