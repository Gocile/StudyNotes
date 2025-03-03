package com.gocile.shikesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gocile.shikesystem.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT admin_name FROM admin WHERE id = #{adminId}")
    String getAdminNameById(String adminId);
}
