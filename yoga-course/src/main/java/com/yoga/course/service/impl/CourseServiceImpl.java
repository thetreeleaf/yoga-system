package com.yoga.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.course.entity.Course;
import com.yoga.course.mapper.CourseMapper;
import com.yoga.course.query.CoursePageQuery;
import com.yoga.course.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.course.vo.CoursePageVO;
import org.apache.ibatis.annotations.Param;
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
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    public IPage<CoursePageVO> listCourseWithPage(@Param("queryParams") CoursePageQuery queryParams) {
        Page<CoursePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<CoursePageVO> list = this.baseMapper.listCourseWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updateCourse(Integer courseId, Course course) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<Course>();
        queryWrapper.eq("id",courseId);
        Course course1 = this.baseMapper.selectOne(queryWrapper);
        if (course1 == null) {
            return false;
        }
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",course1.getId()).
                set("course_name",course.getCourseName()).
                set("course_desc",course.getCourseDesc()).
                set("stroe_time",course.getStroeTime()).
                set("course_type",course.getCourseType()).
                set("price",course.getPrice()).
                set("place_id",course.getPlaceId());
        return this.baseMapper.update(null, wrapper) > 0 ? true : false;
    }
}
