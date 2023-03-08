package com.yoga.course.mapper;

import com.yoga.course.entity.CourseDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.course.query.CourseDetailQuery;
import com.yoga.course.vo.CourseDetailVO;
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
public interface CourseDetailMapper extends BaseMapper<CourseDetail> {

    List<CourseDetailVO> listPlaceCourse(@Param("placeId") Integer placeId, @Param("queryParams") CourseDetailQuery queryParams);

    List<CourseDetailVO> getCourse(@Param("weeks") String[] weeks, @Param("placeId") Integer placeId,@Param("times") String[] times);
}
