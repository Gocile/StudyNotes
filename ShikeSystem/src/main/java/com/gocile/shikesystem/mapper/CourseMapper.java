package com.gocile.shikesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gocile.shikesystem.model.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT course.* " +
            "FROM course " +
            "INNER JOIN stu_course  ON course.id = stu_course.course_id " +
            "WHERE stu_course.stu_id = #{stuId} AND course.semester = #{semester}")
    List<Course> getCourseBySemester(String stuId, String semester);


    @Select("SELECT course.* " +
            "FROM course " +
            "JOIN stu ON course.grade = stu.grade " +
            "AND course.college = stu.college " +
            "AND course.major = stu.major " +
            "AND course.optionality = '可选' " +
            "WHERE stu.id = #{stuId} " +
            "AND course.category = #{category}")
    List<Course> getCourseByCategory(String stuId,String category);


    @Select("SELECT admin_id FROM course WHERE id = #{courseId}")
    String getAdminIdByCourseId(String courseId);

}
