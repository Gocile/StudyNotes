package com.gocile.shikesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gocile.shikesystem.mapper.AdminMapper;
import com.gocile.shikesystem.mapper.CourseMapper;
import com.gocile.shikesystem.mapper.InformationMapper;
import com.gocile.shikesystem.model.Admin;
import com.gocile.shikesystem.model.Course;
import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
//import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminService {
    private final AdminMapper adminMapper;
    private final CourseMapper courseMapper;
    private final InformationMapper informationMapper;

    public Admin getInfo(String id){
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

    public boolean changeInfo(Admin admin){
        try{
            adminMapper.updateById(admin);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public BaseResponse<String> insertCourse(String id,String title,int grade, String college,
                                             String major,int capacity,String optionality,String category,String semester){
        Course course = null;
        try {
            String adminName = adminMapper.getAdminNameById(id);
            course = new Course(id,title,grade,college,major,capacity,optionality,category,semester,adminName,id,0);
            courseMapper.insert(course);
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("添加失败，请重试")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(true)
                .msg("已添加")
                .build();
    }

    public BaseResponse<String> deleteCourse(String adminId,String courseId){
        try {
            //根据courseId到information中查询对应的选课时间
            LocalDateTime selectTime = informationMapper.getSelectTimeById(courseId,adminId).get(0);
            //如果选课时间还没到，就允许删除，否则返回提示信息
            LocalDateTime now = LocalDateTime.now();
            if(selectTime.isAfter(now)){
                courseMapper.deleteById(courseId);
                return BaseResponse.<String>builder()
                        .success(true)
                        .msg("已删除")
                        .build();
            }else {
                return BaseResponse.<String>builder()
                        .success(false)
                        .msg("已经被选的课程不能删除")
                        .build();
            }
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("删除失败，请重试")
                    .build();
        }
    }


    public BaseResponse<String> changeCourse(String adminId,String courseId,Course course){
        try {
            //根据courseId到information中查询对应的选课时间
            LocalDateTime selectTime = informationMapper.getSelectTimeById(courseId,adminId).get(0);
            //如果选课时间还没到，就允许修改，否则返回提示信息
            LocalDateTime now = LocalDateTime.now();
            if(selectTime.isAfter(now)){
                courseMapper.updateById(course);
                return BaseResponse.<String>builder()
                        .success(true)
                        .msg("已修改")
                        .build();
            }else {
                return BaseResponse.<String>builder()
                        .success(false)
                        .msg("已经被选的课程不能修改")
                        .build();
            }
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("修改失败，请重试")
                    .build();
        }
    }

    public BaseResponse<List<Course>> getCourse(String adminId,long current,long size){
        try{
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_id",adminId);
            Page<Course> page = new Page<>(current,size);
            Page<Course> coursePage = courseMapper.selectPage(page,queryWrapper);
            List<Course> courseList = coursePage.getRecords();
            return BaseResponse.<List<Course>>builder()
                    .success(true)
                    .msg("查询成功")
                    .data(courseList)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<List<Course>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
    }

    public BaseResponse<String> uploadExcel(String adminId, MultipartFile file){
        try {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",adminId);
            String adminName = adminMapper.selectList(queryWrapper).get(0).getName();
            courseMapper.insert(ExcelUtil.parseCourseExcel(adminId,adminName,file));
            return BaseResponse.<String>builder()
                    .success(true)
                    .msg("导入成功")
                    .build();
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("导入失败，请重试")
                    .build();
        }
    }
}
