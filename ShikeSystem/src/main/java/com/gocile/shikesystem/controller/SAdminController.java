package com.gocile.shikesystem.controller;

import com.gocile.shikesystem.mapper.AdminMapper;
import com.gocile.shikesystem.mapper.StuMapper;
import com.gocile.shikesystem.model.*;
import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.service.SAdminService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sadmin")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SAdminController {
    private final SAdminService sAdminService;

    boolean success = false;

    @GetMapping("/stu")
    public BaseResponse<List<Stu>> getStu(ServletRequest servletRequest,long current,long size){
        return sAdminService.getStu(current,size);
    }

    @GetMapping("/admin")
    public BaseResponse<List<Admin>> getAdmin(ServletRequest servletRequest,long current,long size){
        return sAdminService.getadmin(current,size);
    }

    @PostMapping("/user")
    public BaseResponse<String> insertUser(ServletRequest servletRequest,String id,String identity,String name,String gender,
                                           String phoneNum,String email,String college,String major,int grade){
        boolean success = false;
        if(identity.equals("学生")){
            //调用学生的注册
            success = sAdminService.insertStu(id,name,gender,phoneNum,email,college,major,grade);
        }else if (identity.equals("管理员")){
            //调用管理员的注册
            success = sAdminService.insertAdmin(id,name,gender,phoneNum,email,college);
        }else {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("身份只能为学生或管理员")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(success)
                .msg(success?"已添加":"添加失败，请重试")
                .build();
    }

    @DeleteMapping("/user")
    public BaseResponse<String> deleteUser(ServletRequest servletRequest,String identity,String id){
        if(identity.equals("学生")){
            //调用学生的删除
            success = sAdminService.deleteStu(id);
        }else if (identity.equals("管理员")){
            //调用管理员的删除
            success = sAdminService.deleteAdmin(id);
        }else {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("身份只能为学生或管理员")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(success)
                .msg(success?"已删除":"删除失败，请重试")
                .build();
    }

    @PutMapping("/user")
    public BaseResponse<String> changeUser(ServletRequest servletRequest,String identity,String id,String name,
                                           String gender, String phoneNum,String email,String college,String major,
                                           int grade){
        if(identity.equals("学生")){
            //调用学生的修改
            success = sAdminService.changeStu(id,name,gender,phoneNum,email,college,major,grade);
        }else if (identity.equals("管理员")){
            //调用管理员的修改
            success = sAdminService.changeAdmin(id,name,gender,phoneNum,email,college);
        }else {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("身份只能为学生或管理员")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(success)
                .msg(success?"已修改":"修改失败，请重试")
                .build();
    }

    //批量导入excel
    @PostMapping("/excel")
    public BaseResponse<String> uploadExcel(MultipartFile file){
        return sAdminService.uploadExcel(file);
    }

    //对information表进行操作--Put
    @PutMapping("/information")
    public BaseResponse<String> insertInformation(int grade, String college, String major, String category,
                                                  LocalDateTime selectTime,int maxQuantity){
        return sAdminService.insertInformation(grade,college,major,category,selectTime,maxQuantity);
    }

    @GetMapping("/evaluate/admin")
    public BaseResponse<List<AdminEvaluate>> getAdminEvaluate(long current,long size){
        return sAdminService.getAdminEvaluate(current,size);
    }

    @GetMapping("/evaluate/course")
    public BaseResponse<List<CourseEvaluate>> getCourseEvaluate(long current, long size){
        return sAdminService.getCourseEvaluate(current,size);
    }

    @PostMapping("/evaluate/admin")
    public BaseResponse<String> auditAdminEvaluate(int id,boolean passed){
        return sAdminService.auditAdminEvaluate(id,passed);
    }

    @PostMapping("/evaluate/course")
    public BaseResponse<String> auditCourseEvaluate(int id,boolean passed){
        return sAdminService.auditCourseEvaluate(id,passed);
    }
}