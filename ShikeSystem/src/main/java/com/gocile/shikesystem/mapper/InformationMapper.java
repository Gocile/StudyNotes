package com.gocile.shikesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gocile.shikesystem.model.Information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface InformationMapper extends BaseMapper<Information> {
    @Select("SELECT information.category,information.select_time "+
            "FROM information "+
            "JOIN stu "+
            "ON (information.grade=stu.grade OR information.grade IS NULL) " +
                "AND (information.college=stu.college OR information.college IS NULL) " +
                "AND(information.major=stu.major OR information.major IS NULL) " +
                "AND information.select_time>NOW() "+
            "WHERE stu.id = #{stuId}")
    List<Map<String, LocalDateTime>> getCourseCategory(String stuId);

    @Select("SELECT select_time "+
            "FROM information "+
            "WHERE (grade IS NULL OR grade = #{grade}) "+
            "AND (college IS NULL OR college = #{college}) "+
            "AND (major IS NULL OR major = #{major}) "+
            "AND (category = #{category} )")
    List<LocalDateTime> getSelectTimeByInfo(int grade,String college,String major,String category);

    @Select("SELECT i.select_time FROM information i JOIN course c"+
            "ON (i.grade = c.grade OR I.grade IS NULL)"+
            "AND (i.college = c.college OR i.college IS NULL)"+
            "AND (i.major = c.major OR i.major IS NULL)"+
            "WHERE c.id = #{courseId} AND c.admin_id = #{adminId}")
    List<LocalDateTime> getSelectTimeById(String courseId,String adminId);
}
