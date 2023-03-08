package com.yoga.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.course.entity.CourseState;
import com.yoga.course.mapper.CourseStateMapper;
import com.yoga.course.query.CourseStatePageQuery;
import com.yoga.course.service.CourseStateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.course.vo.CoursePageVO;
import com.yoga.course.vo.CourseStatePageVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Service
public class CourseStateServiceImpl extends ServiceImpl<CourseStateMapper, CourseState> implements CourseStateService {

    @Override
    public IPage<CourseStatePageVO> listCourseStateWithPage(CourseStatePageQuery queryParams) {
        Page<CourseStatePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<CourseStatePageVO> list = this.baseMapper.listCourseStateWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }
}
