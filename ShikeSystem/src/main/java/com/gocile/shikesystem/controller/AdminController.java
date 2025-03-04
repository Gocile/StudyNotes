package com.gocile.shikesystem.controller;

import com.gocile.shikesystem.model.Admin;
import com.gocile.shikesystem.model.Course;
import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Tag(name = "admin",description = "管理员可访问接口")
public class AdminController {
    final AdminService adminService;

    boolean success = false;

    @Operation(summary = "查询个人信息")
    @GetMapping("/info")
    public BaseResponse<Admin> getInfo(ServletRequest servletRequest){
        String id = (String) servletRequest.getAttribute("id");
        //查询所有信息，获取一个admin对象
        Admin admin = null;
        try {
            admin = adminService.getInfo(id);
            success = true;
        }catch (Exception e){
            success = false;
        }
        return BaseResponse.<Admin>builder()
                .success(true)
                .msg(success?"查询成功":"查询失败，请重试")
                .data(admin)
                .build();
    }

    @Operation(summary = "修改部分个人信息")
    @PutMapping("/info")
    public BaseResponse<String> changeInfo(ServletRequest servletRequest, String email, String phoneNum, String pwd){
        String id = (String) servletRequest.getAttribute("id");
        Admin admin = new Admin(id,email,phoneNum,pwd);
        //修改，返回boolean
        success = adminService.changeInfo(admin);
        return BaseResponse.<String>builder()
                .success(success)
                .msg(success?"修改成功":"修改失败，请重试")
                .build();
    }

    @Operation(summary = "增加课程")
    @PostMapping("/course")
    public BaseResponse<String> insertCourse(ServletRequest servletRequest,String title,int grade, String college,
                                             String major,int capacity,String optionality,String category,String semester){
        String id = (String) servletRequest.getAttribute("id");
        return adminService.insertCourse(id,title,grade,college,major,capacity,optionality,category,semester);
    }

    @Operation(summary = "根据课程id删除选课前课程")
    @DeleteMapping("/course")
    public BaseResponse<String> deleteCourse(ServletRequest servletRequest,String courseId){
        String id = (String) servletRequest.getAttribute("id");
        return adminService.deleteCourse(id,courseId);
    }

    @Operation(summary = "修改课程")
    @PutMapping("/course")
    public BaseResponse<String> changeCourse(ServletRequest servletRequest,String courseId,String title,int grade, String college,
                                             String major,int capacity,String optionality,String category,String semester){
        Course course = new Course(courseId,title,grade,college,major,capacity,optionality,category,semester);
        String id = (String) servletRequest.getAttribute("id");
        return adminService.changeCourse(id,courseId,course);
    }

    @Operation(summary = "分页查询课程")
    @GetMapping("/course")
    public BaseResponse<List<Course>> getCourse(ServletRequest servletRequest,long current,long size){
        String id = (String) servletRequest.getAttribute("id");
        return adminService.getCourse(id,current,size);
    }


    @Operation(summary = "导入课程的excel文件")
    @PostMapping("/excel")
    public BaseResponse<String> uploadExcel(ServletRequest servletRequest, MultipartFile file){
        String id = (String) servletRequest.getAttribute("id");
        return adminService.uploadExcel(id,file);
    }
}
