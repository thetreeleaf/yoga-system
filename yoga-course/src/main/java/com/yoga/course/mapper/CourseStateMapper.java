package com.yoga.course.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.course.entity.CourseState;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.course.query.CourseStatePageQuery;
import com.yoga.course.vo.CourseStatePageVO;
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
public interface CourseStateMapper extends BaseMapper<CourseState> {

    List<CourseStatePageVO> listCourseStateWithPage(@Param("page") Page<CourseStatePageVO> page,@Param("queryParams") CourseStatePageQuery queryParams);
}
