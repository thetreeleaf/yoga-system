package com.yoga.course.service;

import com.yoga.course.entity.CourseDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.course.query.CourseDetailQuery;
import com.yoga.course.vo.CourseDetailVO;
import com.yoga.course.vo.CourseVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
public interface CourseDetailService extends IService<CourseDetail> {

    List<CourseDetailVO> listPlaceCourse(@Param("placeId") Integer placeId, @Param("queryParams") CourseDetailQuery queryParams) throws Throwable;

    List<CourseVO> PlaceCourseTable(Integer placeId, Integer data);
}
