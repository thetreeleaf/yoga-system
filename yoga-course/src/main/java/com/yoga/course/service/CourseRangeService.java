package com.yoga.course.service;

import com.yoga.course.dto.CourseRangeDto;
import com.yoga.course.entity.CourseRange;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
public interface CourseRangeService extends IService<CourseRange> {

    CourseRange getCourseRangeDetail(Integer courseId);

    boolean addCourseRange(CourseRangeDto courseRangeDto);

    boolean updateCourseRange(Integer courseRangeId, CourseRangeDto courseRangeDto);
}
