package com.yoga.course.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.course.query.CoursePageQuery;
import com.yoga.course.vo.CoursePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    List<CoursePageVO> listCourseWithPage(@Param("page") Page<CoursePageVO> page,@Param("queryParams") CoursePageQuery queryParams);
}
