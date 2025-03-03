package com.gocile.shikesystem.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gocile.shikesystem.mapper.*;
import com.gocile.shikesystem.model.*;
import com.gocile.shikesystem.response.BaseResponse;
import com.gocile.shikesystem.util.CreatPwdUtil;
import com.gocile.shikesystem.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SAdminService extends ServiceImpl<AdminEvaluateMapper,AdminEvaluate> {
    private final StuMapper stuMapper;
    private final AdminMapper adminMapper;
    private final InformationMapper informationMapper;
    private final AdminEvaluateMapper adminEvaluateMapper;
    private final CourseEvaluateMapper courseEvaluateMapper;


    public BaseResponse<List<Stu>> getStu(long current,long size){
        try {
            Page<Stu> page = new Page<>(current,size);
            Page<Stu> stuPage = stuMapper.selectPage(page,null);
            List<Stu> stuList = stuPage.getRecords();
            return BaseResponse.<List<Stu>>builder()
                    .success(true)
                    .msg("查询成功")
                    .data(stuList)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<List<Stu>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
    }


    public BaseResponse<List<Admin>> getadmin(long current, long size){
        try {
            Page<Admin> page = new Page<>(current,size);
            Page<Admin> adminPage = adminMapper.selectPage(page,null);
            List<Admin> adminList = adminPage.getRecords();
            return BaseResponse.<List<Admin>>builder()
                    .success(true)
                    .msg("查询成功")
                    .data(adminList)
                    .build();
        } catch (Exception e) {
            return BaseResponse.<List<Admin>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
    }


    public boolean insertStu(String id,String name,String gender,
                             String phoneNum,String email,String college,String major,int grade){
        try {
            //自动生成pwd：姓名首拼大写加学号
            String pwd = CreatPwdUtil.getInitialUppercase(name)+id;
            Stu stu = new Stu(id,pwd,name,gender,phoneNum,email,college,major,grade);
            return stuMapper.insert(stu) == 1;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean insertAdmin(String id,String name,String gender,
                             String phoneNum,String email,String college){
        try {
            //自动生成pwd：姓名首拼大写加工号
            String pwd = CreatPwdUtil.getInitialUppercase(name)+id;
            Admin admin = new Admin(id,pwd,name,gender,phoneNum,email,college);
            return adminMapper.insert(admin) == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteStu(String id){
        try {
            stuMapper.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteAdmin(String id){
        try {
            adminMapper.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changeStu(String id,String name,String gender,String phoneNUm,String email,String college,String major,int grade){
        try {
            Stu stu = new Stu(id,name,gender,phoneNUm,email,college,major,grade);
            stuMapper.updateById(stu);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean changeAdmin(String id,String name,String gender,String phoneNUm,String email,String college){
        try {
            Admin admin = new Admin(id,name,gender,phoneNUm,email,college);
            adminMapper.updateById(admin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BaseResponse<String> insertInformation(int grade, String college, String major, String category,
                                                  LocalDateTime selectTime, int maxQuantity){
        try {
            Information information = new Information(grade,college,major,category,selectTime,maxQuantity);
            if(informationMapper.insert(information)!=1){
                throw new Exception();
            }
            return BaseResponse.<String>builder()
                    .success(true)
                    .msg("设置成功")
                    .build();
        }catch (Exception e){
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("设置失败，请重试")
                    .build();
        }
    }

    public BaseResponse<String> uploadExcel(MultipartFile file){
        try {
            List<ExcelData> excelDataList = ExcelUtil.parseUserExcel(file);
            for (ExcelData excelData : excelDataList) {
                if (excelData.getIdentity() == 1) {
                    Stu stu = new Stu(excelData.getId(), excelData.getName(), excelData.getGender(),
                            excelData.getPhoneNum(), excelData.getEmail(), excelData.getCollege(),
                            excelData.getMajor(), excelData.getGrade());
                    stuMapper.insert(stu);
                } else if (excelData.getIdentity() == 2) {
                    Admin admin = new Admin(excelData.getId(), excelData.getName(), excelData.getGender(),
                            excelData.getPhoneNum(), excelData.getEmail(), excelData.getCollege());
                    adminMapper.insert(admin);
                }
            }
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

    public BaseResponse<List<AdminEvaluate>> getAdminEvaluate(long current,long size){
        try {
            Page<AdminEvaluate> page = new Page<>(current,size);
            Page<AdminEvaluate> adminEvaluatePage = adminEvaluateMapper.selectPage(page,null);
            List<AdminEvaluate> adminEvaluateList = adminEvaluatePage.getRecords();
            return BaseResponse.<List<AdminEvaluate>>builder()
                    .success(true)
                    .msg("查询成功")
                    .data(adminEvaluateList)
                    .build();
        }catch (Exception e){
            return BaseResponse.<List<AdminEvaluate>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
    }

    public BaseResponse<List<CourseEvaluate>> getCourseEvaluate(long current, long size){
        try {
            Page<CourseEvaluate> page = new Page<>(current,size);
            Page<CourseEvaluate> courseEvaluatePage = courseEvaluateMapper.selectPage(page,null);
            List<CourseEvaluate> courseEvaluateList = courseEvaluatePage.getRecords();
            return BaseResponse.<List<CourseEvaluate>>builder()
                    .success(true)
                    .msg("查询成功")
                    .data(courseEvaluateList)
                    .build();
        }catch (Exception e){
            return BaseResponse.<List<CourseEvaluate>>builder()
                    .success(false)
                    .msg("查询失败，请重试")
                    .build();
        }
    }


    public BaseResponse<String> auditAdminEvaluate(int id,boolean passed){
        try{
            int passedInt = passed?1:0;
            AdminEvaluate adminEvaluate = new AdminEvaluate(id,1,passedInt);
            adminEvaluateMapper.updateById(adminEvaluate);
            return BaseResponse.<String>builder()
                    .success(true)
                    .msg("已更新审核信息")
                    .build();
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("更新审核信息失败，请重试")
                    .build();
        }
    }

    public BaseResponse<String> auditCourseEvaluate(int id,boolean passed){
        try{
            int passedInt = passed?1:0;
            CourseEvaluate courseEvaluate = new CourseEvaluate(id,1,passedInt);
            courseEvaluateMapper.updateById(courseEvaluate);
            return BaseResponse.<String>builder()
                    .success(true)
                    .msg("已更新审核信息")
                    .build();
        } catch (Exception e) {
            return BaseResponse.<String>builder()
                    .success(false)
                    .msg("更新审核信息失败，请重试")
                    .build();
        }
    }

}
