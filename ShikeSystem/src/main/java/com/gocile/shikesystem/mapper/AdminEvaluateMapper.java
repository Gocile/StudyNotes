package com.gocile.shikesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gocile.shikesystem.model.AdminEvaluate;
import com.gocile.shikesystem.model.Evaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminEvaluateMapper extends BaseMapper<AdminEvaluate> {
    @Select("SELECT content,audited,passed FROM admin_evaluate WHERE stu_id = #{stuId}")
    List<Evaluate> getMyEvaluate(String stuId);
}
