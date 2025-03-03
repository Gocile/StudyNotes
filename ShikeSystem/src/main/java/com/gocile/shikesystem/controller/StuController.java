package com.gocile.shikesystem.controller;

import com.gocile.shikesystem.model.Course;
import com.gocile.shikesystem.model.Evaluate;
import com.gocile.shikesystem.model.Stu;
import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.service.StuService;
import jakarta.servlet.ServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stu")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StuController {
    private final StuService stuService;

    boolean success = false;

    @GetMapping("/info")//查询所有个人信息
    public BaseResponse<Stu> getInfo(ServletRequest servletRequest){
        String id = (String) servletRequest.getAttribute("id");
        //查询所有信息，获取一个stu对象
        Stu stu = null;
        try {
            stu = stuService.getInfo(id);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        return BaseResponse.<Stu>builder()
                .success(success)
                .msg(success?"查询成功":"查询失败，请重试")
                .data(stu)
                .build();
    }

    @PutMapping("/info")//修改部分个人信息
    public BaseResponse<String> changeInfo(ServletRequest servletRequest,String email,String phoneNum,String pwd){
        String id = (String) servletRequest.getAttribute("id");
        Stu stu = new Stu(id,email,phoneNum,pwd);
        //修改部分信息，返回boolean
        success = stuService.changeInfo(stu);
        return BaseResponse.<String>builder()
                .success(success)
                .msg(success?"修改成功":"修改失败，请重试")
                .build();
    }

    @GetMapping("/course/bySemester")
    public BaseResponse<List<Course>> getCourseBySemester(ServletRequest servletRequest,String semester){
        String id = (String) servletRequest.getAttribute("id");
        //查询这个学期的已选/必修课，返回List
        List<Course> courseList = null;
        try {
            courseList = stuService.getCourseBySemester(id,semester);
            success = true;
        } catch (Exception e) {
            success = false;
        }
        return BaseResponse.<List<Course>>builder()
                .success(success)
                .msg(success?"查询成功":"查询失败，请重试")
                .data(courseList)
                .build();
    }

    @GetMapping("/course/category")
    public BaseResponse<Map<String, LocalDateTime>> getCourseCategory(ServletRequest servletRequest){
        String id = (String) servletRequest.getAttribute("id");
        Map<String,LocalDateTime> map = stuService.getCourseCategory(id);
        return BaseResponse.<Map<String,LocalDateTime>>builder()
                .success(success)
                .msg(success?"查询成功":"查询失败，请重试")
                .data(map)
                .build();
    }

    @GetMapping("/course/byCategory")
    public BaseResponse<List<Course>> getCourseByCategory(ServletRequest servletRequest,String category){
        String id = (String) servletRequest.getAttribute("id");
        List<Course> courseList = null;
        //查询这个类别的课的详细信息，返回List
        try{
            courseList = stuService.getCourseByCategory(id,category);
            success = true;
        }catch (Exception e){
            success = false;
        }
        return BaseResponse.<List<Course>>builder()
                .success(success)
                .msg(success?"查询成功":"查询失败，请重试")
                .data(courseList)
                .build();
    }

    @PostMapping("/course")//输入课程id选课
    public BaseResponse<String> insertCourse(ServletRequest servletRequest,String courseId){
        String id = (String) servletRequest.getAttribute("id");
        //选课，返回base response
        BaseResponse<String> baseResponse = stuService.insertCourse(id,courseId);
        return baseResponse;
    }

    @DeleteMapping("/course")//输入课程id退课
    public BaseResponse<String> deleteCourse(ServletRequest servletRequest,String courseId){
        String id = (String) servletRequest.getAttribute("id");
        //退课，返回base response
        BaseResponse<String> baseResponse = stuService.deleteCourse(id,courseId);
        return baseResponse;
    }

    @PostMapping("/evaluate/course")
    public BaseResponse<String> insertCourseEvaluate(ServletRequest servletRequest,String content,String courseId){
        String id = (String) servletRequest.getAttribute("id");
        //添加评价，返回base response
        BaseResponse<String> baseResponse= stuService.insertEvaluate(id,content,courseId,0);
        return baseResponse;
    }

    @PostMapping("/evaluate/admin")
    public BaseResponse<String> insertAdminEvaluate(ServletRequest servletRequest,String content,String courseId){
        String id = (String) servletRequest.getAttribute("id");
        //根据课程id找到管理员id，添加评价，返回base response
        BaseResponse<String> baseResponse= stuService.insertEvaluate(id,content,courseId,1);
        return baseResponse;
    }

    @GetMapping("/evaluate/course")
    public BaseResponse<List<String>> getCourseEvaluate(String courseTitle){
        //返回content构成的list
        BaseResponse<List<String>> baseResponse = stuService.getCourseEvaluate(courseTitle);
        return baseResponse;
    }

    @GetMapping("/evaluate/admin")
    public BaseResponse<List<String>> getAdminEvaluate(String adminName){
        //返回content构成的list
        BaseResponse<List<String>> baseResponse = stuService.getAdminEvaluate(adminName);
        return baseResponse;
    }

    @GetMapping("/evaluate/mine")
    public BaseResponse<List<Evaluate>> getMyEvaluate(ServletRequest servletRequest){
        String id = (String) servletRequest.getAttribute("id");
        BaseResponse<List<Evaluate>> baseResponse = stuService.getMyEvaluate(id);
        return baseResponse;
    }
}