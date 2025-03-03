package com.gocile.shikesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gocile.shikesystem.mapper.*;
import com.gocile.shikesystem.model.*;
import com.gocile.shikesystem.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class StuService {
    private final StuMapper stuMapper;
    private final CourseMapper courseMapper;
    private final InformationMapper informationMapper;
    private final StuCourseMapper stuCourseMapper;
    private final CourseEvaluateMapper courseEvaluateMapper;
    private final AdminEvaluateMapper adminEvaluateMapper;
    private final AdminMapper adminMapper;

    public Stu getInfo(String id){
        Stu stu = stuMapper.selectById(id);
        return stu;
    }

    public boolean changeInfo(Stu stu){
        try {
            stuMapper.updateById(stu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Course> getCourseBySemester(String id,String semester){
        List<Course> courseList = courseMapper.getCourseBySemester(id, semester);
        return courseList;
    }

    public Map<String, LocalDateTime> getCourseCategory(String id){
        List<Map<String,LocalDateTime>> mapList = informationMapper.getCourseCategory(id);
        System.out.println(mapList);
        return null;
    }

    public List<Course> getCourseByCategory(String id, String category){
        List<Course> courseList = courseMapper.getCourseByCategory(id,category);
        return courseList;
    }

    public BaseResponse<String> insertCourse(String stuId,String courseId){
        try {
            //首先根据选课申请的信息查询学生和课程的信息
            Stu stu = stuMapper.selectById(stuId);
            Course course = courseMapper.selectById(courseId);
            //这里要验证课程的选课时间是不是已经开始了
            List<LocalDateTime> selectTimeList = informationMapper.getSelectTimeByInfo(course.getGrade(), course.getCollege(),
                    course.getMajor(), course.getCategory());
            LocalDateTime selectTime = selectTimeList.get(0);
            LocalDateTime now = LocalDateTime.now();
            boolean sufficient = true;
            if (course.getCategory().equals("专业课程") && stu.getMajorQuantity() > 0) {
                stu.setMajorQuantity(stu.getMajorQuantity() - 1);
            } else if (course.getCategory().equals("体育") && stu.getMajorQuantity() > 0) {
                stu.setPeQuantity(stu.getPeQuantity() - 1);
            } else if (course.getCategory().equals("素质课程") && stu.getMajorQuantity() > 0) {
                stu.setCultureQuantity(stu.getCultureQuantity() - 1);
            } else {
                sufficient = false;
            }
            //如果学生的确在这个课程面向的范围内，课已经开始选了，课的容量还有剩余，学生选课数还没达到最大选课数，就选课
            boolean selectAble = now.isAfter(selectTime) && stu.getGrade() == course.getGrade() &&
                    stu.getCollege().equals(course.getCollege()) && stu.getMajor().equals(course.getMajor())
                    && course.getCapacity() > 0 && sufficient;
            //选课
            if (selectAble) {
                StuCourse stuCourse = new StuCourse(stuId, courseId,0);
                stuCourseMapper.insert(stuCourse);//把学生和课程的关系记录下来
                stuMapper.updateById(stu);//更新学生的选课容量
                course.setCapacity(course.getCapacity() - 1);
                courseMapper.updateById(course);//更新课程的容量
                return BaseResponse.<String>builder()
                        .success(true)
                        .msg("选课成功")
                        .build();
            }
        } catch (Exception e) {
            //不做处理，返回下面的失败信息即可
        }
        return BaseResponse.<String>builder()
                .success(false)
                .msg("选课失败，请重试")
                .build();
    }

    public BaseResponse<String> deleteCourse(String stuId,String courseId) {
        try {
            Stu stu = stuMapper.selectById(stuId);
            Course course = courseMapper.selectById(courseId);
            if(course.getCategory().equals("专业课程")) stu.setMajorQuantity(stu.getMajorQuantity()+1);
            if(course.getCategory().equals("体育")) stu.setPeQuantity(stu.getPeQuantity()+1);
            if(course.getCategory().equals("素质课程")) stu.setCultureQuantity(stu.getCultureQuantity()+1);
            course.setCapacity(course.getCapacity()+1);
            QueryWrapper<StuCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("stu_id",stuId).eq("course_id",courseId);
            int status = stuCourseMapper.selectList(queryWrapper).get(0).getStatus();
            if(status == 0){
                stuCourseMapper.delete(queryWrapper);
                stuMapper.updateById(stu);
                courseMapper.updateById(course);
            }else {
                Exception e = new Exception();
            }
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("退课失败，请重试")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(true)
                .msg("退课成功")
                .build();
    }

    public BaseResponse<String> insertEvaluate(String stuId,String content,String courseId,int flag){
        //flag：0为course的评价，1为admin的评价
        try{
            //先判断该学生是否上过该课程
            QueryWrapper<StuCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("stu_id",stuId).eq("course_id",courseId);
            List<StuCourse> stuCourseList = stuCourseMapper.selectList(queryWrapper);
            if(stuCourseList.isEmpty()) throw new  Exception();
            //再新增评价
            if(flag == 0){
                CourseEvaluate courseEvaluate = new CourseEvaluate(content,stuId,courseId);
                courseEvaluateMapper.insert(courseEvaluate);
            }else {
                //根据课程id查询管理员id
                String adminId = courseMapper.getAdminIdByCourseId(courseId);
                AdminEvaluate adminEvaluate = new AdminEvaluate(content,stuId,adminId);
                adminEvaluateMapper.insert(adminEvaluate);
            }
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("添加评价失败，请重试")
                    .build();
        }
        return BaseResponse.<String>builder()
                .success(true)
                .msg("已添加评价")
                .build();
    }

    public BaseResponse<List<String>> getCourseEvaluate(String courseTitle){
        List<String> contentList;
        try {
            //根据course title模糊查询id的list
            QueryWrapper<Course> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.like("title",courseTitle).select("id");
            List<String> idList = courseMapper.selectObjs(queryWrapper1);
            //利用循环，把list中每一个id都查一遍，过滤没通过审核的，收集content
            QueryWrapper<CourseEvaluate> queryWrapper2 = new QueryWrapper<>();
            contentList = new ArrayList<>();
            for (int i = 0; i < idList.size(); i++) {
                queryWrapper2.eq("course_id",idList.get(i)).eq("passed",1);
                List<CourseEvaluate> courseEvaluateList = courseEvaluateMapper.selectList(queryWrapper2);
                for (int i1 = 0; i1 < courseEvaluateList.size(); i1++) {
                    contentList.add(courseEvaluateList.get(i1).getContent());
                }
            }
        } catch (Exception e) {
            return BaseResponse.<List<String>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
        return BaseResponse.<List<String>>builder()
                .success(true)
                .msg("查询成功")
                .data(contentList)
                .build();
    }


    public BaseResponse<List<String>> getAdminEvaluate(String adminName){
        List<String> contentList;
        try {
            //根据admin name模糊查询id的list
            QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.like("admin_name",adminName).select("id");
            List<String> idList = adminMapper.selectObjs(queryWrapper1);
            //利用循环，把list中每一个id都查一遍，过滤没通过审核的，收集content
            QueryWrapper<AdminEvaluate> queryWrapper2 = new QueryWrapper<>();
            contentList = new ArrayList<>();
            for (int i = 0; i < idList.size(); i++) {
                queryWrapper2.eq("admin_id",idList.get(i)).eq("passed",1);
                List<AdminEvaluate> adminEvaluateList = adminEvaluateMapper.selectList(queryWrapper2);
                for (int i1 = 0; i1 < adminEvaluateList.size(); i1++) {
                    contentList.add(adminEvaluateList.get(i1).getContent());
                }
            }
        } catch (Exception e) {
            return BaseResponse.<List<String>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
        return BaseResponse.<List<String>>builder()
                .success(true)
                .msg("查询成功")
                .data(contentList)
                .build();
    }

    public BaseResponse<List<Evaluate>> getMyEvaluate(String stuId){
        List<Evaluate> evaluateList = new ArrayList<>(List.of());
        try {
            evaluateList.addAll(courseEvaluateMapper.getMyEvaluate(stuId));
            evaluateList.addAll(adminEvaluateMapper.getMyEvaluate(stuId));
        } catch (Exception e) {
            return BaseResponse.<List<Evaluate>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
        return BaseResponse.<List<Evaluate>>builder()
                .success(true)
                .msg("查询成功")
                .data(evaluateList)
                .build();
    }
}
