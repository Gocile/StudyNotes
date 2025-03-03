package com.gocile.shikesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gocile.shikesystem.mapper.AdminMapper;
import com.gocile.shikesystem.mapper.StuMapper;
import com.gocile.shikesystem.model.Admin;
import com.gocile.shikesystem.model.Stu;
import com.gocile.shikesystem.response.BaseResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoginService {
    private final StuMapper stuMapper;
    private final AdminMapper adminMapper;

    public LoginService(StuMapper stuMapper, AdminMapper adminMapper) {
        this.stuMapper = stuMapper;
        this.adminMapper = adminMapper;
    }

    public BaseResponse<String> userLogin(String phoneNum, String id, String pwd, String identity){
        String tokenId = null;
        boolean success;
        if(phoneNum==null){
            //查询id，pwd是否存在
            if(identity.equals("学生")){
                //学生，id，mapper
                QueryWrapper<Stu> queryWrapperOfStu = new QueryWrapper<>();
                queryWrapperOfStu.eq("id",id)
                        .eq("stu_password",pwd);
                List<Stu> stuList = stuMapper.selectList(queryWrapperOfStu);
                success = !stuList.isEmpty();
            }else {
                //管理员，id，mapper
                QueryWrapper<Admin> queryWrapperOfAdmin = new QueryWrapper<>();
                queryWrapperOfAdmin.eq("id",id)
                        .eq("admin_password",pwd);
                List<Admin> adminList = adminMapper.selectList(queryWrapperOfAdmin);
                success = !adminList.isEmpty();
            }
            if(!success){//不存在
                return BaseResponse.<String>builder()
                        .success(false)
                        .msg("账号或密码错误")
                        .build();
            }
        }else{
            //查询phoneNum,pwd是否存在
            if(identity.equals("学生")){
                //学生，phoneNum，mapper
                QueryWrapper<Stu> queryWrapperOfStu = new QueryWrapper<>();
                queryWrapperOfStu.eq("phone_num",phoneNum)
                        .eq("stu_password",pwd);
                List<Stu> stuList = stuMapper.selectList(queryWrapperOfStu);
                if (stuList.isEmpty()){
                    success = false;
                }else {
                    success = true;
                    tokenId = stuList.get(0).getId();
                }
            }else {
                //管理员，phoneNum，mapper
                QueryWrapper<Admin> queryWrapperOfAdmin = new QueryWrapper<>();
                queryWrapperOfAdmin.eq("admin_password",pwd);
                List<Admin> adminList = adminMapper.selectList(queryWrapperOfAdmin);
                if (adminList.isEmpty()){
                    success = false;
                }else {
                    success = true;
                    tokenId = adminList.get(0).getId();
                }
            }
            if(!success) {//不存在
                 return BaseResponse.<String>builder()
                        .success(false)
                        .msg("手机号或密码错误")
                        .build();
            }
        }
        return BaseResponse.<String>builder()
                .success(true)
                .msg("登录成功")
                .data(tokenId)
                .build();
    }
}
