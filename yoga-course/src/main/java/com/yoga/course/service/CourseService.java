package com.yoga.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.course.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.course.query.CoursePageQuery;
import com.yoga.course.vo.CoursePageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
public interface CourseService extends IService<Course> {

    IPage<CoursePageVO> listCourseWithPage(@Param("queryParams") CoursePageQuery queryParams);

    boolean updateCourse(Integer courseId, Course course);
}
