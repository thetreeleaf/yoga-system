package com.yoga.course.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.course.entity.CourseState;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.course.query.CourseStatePageQuery;
import com.yoga.course.vo.CourseStatePageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
public interface CourseStateService extends IService<CourseState> {

    IPage<CourseStatePageVO> listCourseStateWithPage(@Param("queryParams") CourseStatePageQuery queryParams);
}
