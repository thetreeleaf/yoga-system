package com.yoga.course.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yoga.course.dto.CourseRangeDto;
import com.yoga.course.entity.CourseDetail;
import com.yoga.course.entity.CourseRange;
import com.yoga.course.entity.TimeRange;
import com.yoga.course.mapper.CourseRangeMapper;
import com.yoga.course.service.CourseDetailService;
import com.yoga.course.service.CourseRangeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.course.service.TimeRangeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Service
public class CourseRangeServiceImpl extends ServiceImpl<CourseRangeMapper, CourseRange> implements CourseRangeService {

    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    private TimeRangeService timeRangeService;

    @Override
    public CourseRange getCourseRangeDetail(Integer courseId) {
        QueryWrapper<CourseRange> wrapper =new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        CourseRange courseRange = this.baseMapper.selectOne(wrapper);
        return courseRange;
    }

    @Override
    @Transactional
    public boolean addCourseRange(CourseRangeDto courseRangeDto) {
        CourseRange courseRange = new CourseRange();
        BeanUtil.copyProperties(courseRangeDto,courseRange);
        boolean saveBatch = false;
        //设置课程安排类型为指定日期
        if (courseRangeDto.getCourseRangeType() == 1 ){
            CourseDetail detail = new CourseDetail();
            BeanUtil.copyProperties(courseRangeDto,detail);
            detail.setWeek(courseRangeDto.getCourseRangeContent());
            saveBatch = courseDetailService.save(detail);
        }
        //设置课程安排类型为周循环
        if (courseRangeDto.getCourseRangeType() == 2) {
            String[] contents = courseRangeDto.getCourseRangeContent().split(",");
            List<CourseDetail> detailList = new ArrayList<>();
            for (String content : contents) {
                CourseDetail detail = new CourseDetail();
                BeanUtil.copyProperties(courseRangeDto,detail);
                detail.setWeek(content);
                detailList.add(detail);
            }
            saveBatch = courseDetailService.saveBatch(detailList);
        }

        if (saveBatch) {
           return this.baseMapper.insert(courseRange) > 0 ? true : false;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateCourseRange(Integer courseRangeId, CourseRangeDto courseRangeDto) {
        CourseRange courseRange = this.baseMapper.selectById(courseRangeId);
        TimeRange timeRange = timeRangeService.getById(courseRange.getTimeId());
        //原先课程安排类型为指定日期
        if(courseRange.getCourseRangeType() == 1 ) {
            QueryWrapper<CourseDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseRangeDto.getCourseId());
            wrapper.eq("range_time", timeRange.getRangeTime());
            if (courseRange.getCoachId() != null) {
                wrapper.eq("coach_id", courseRange.getCoachId());
            }
            wrapper.eq("week", courseRange.getCourseRangeContent());
            CourseDetail one = courseDetailService.getOne(wrapper);
            //现在修改的课程安排类型仍为指定日期
            if (courseRangeDto.getCourseRangeType() == 1) {
                if (courseRangeDto.getCoachId() != null) {
                    one.setCoachId(courseRangeDto.getCoachId());
                }
                one.setRangeTime(courseRangeDto.getRangeTime());
                one.setWeek(courseRangeDto.getCourseRangeContent());
                one.setRoomId(courseRangeDto.getRoomId());
                boolean result = courseDetailService.updateById(one);
                if (result) {
                    CourseRange courseRange1 = new CourseRange();
                    BeanUtils.copyProperties(courseRangeDto, courseRange1);
                    return this.baseMapper.updateById(courseRange1) > 0;
                }
                return result;
            }
            //现在修改的课程安排类型为周循环
            if (courseRangeDto.getCourseRangeType() == 2) {
                boolean remove = courseDetailService.removeById(one);
                if (remove) {
                    List<CourseDetail> detailList = new ArrayList<>();
                    String[] contents = courseRangeDto.getCourseRangeContent().split(",");
                    for (String content : contents) {
                        CourseDetail courseDetail = new CourseDetail();
                        BeanUtils.copyProperties(courseRangeDto, courseDetail);
                        courseDetail.setWeek(content);
                        detailList.add(courseDetail);
                    }
                    boolean saveBatch = courseDetailService.saveBatch(detailList);
                    if (saveBatch) {
                        BeanUtils.copyProperties(courseRangeDto, courseRange);
                        return this.baseMapper.updateById(courseRange) > 0;
                    }
                }
                return remove;
            }

        }

        //原先课程安排类型为周循环
        if (courseRange.getCourseRangeType() == 2) {
            String[] oldContents = courseRange.getCourseRangeContent().split(",");
            //现在修改的课程安排类型为指定日期
            if (courseRangeDto.getCourseRangeType() == 1) {
                //删除周循环数据
                for (String oldContent : oldContents) {
                    QueryWrapper<CourseDetail> delWrapper = new QueryWrapper<>();
                    delWrapper.eq("course_id",courseRangeDto.getCourseId());
                    delWrapper.eq("range_time",timeRange.getRangeTime());
                    if (courseRange.getCoachId() != null) {
                        delWrapper.eq("coach_id",courseRange.getCoachId());
                    }
                    delWrapper.eq("week",oldContent);
                    CourseDetail delOne = courseDetailService.getOne(delWrapper);
                    courseDetailService.removeById(delOne);
                }
                //增加指定日期数据
                CourseDetail addDetail = new CourseDetail();
                BeanUtils.copyProperties(courseRangeDto,addDetail);
                addDetail.setWeek(courseRangeDto.getCourseRangeContent());
                boolean addResult = courseDetailService.save(addDetail);
                if (addResult) {
                    CourseRange range = new CourseRange();
                    BeanUtils.copyProperties(courseRangeDto,range);
                    return this.baseMapper.updateById(range) > 0;
                }
                return addResult;
            }

            //现在修改的课程安排类型仍为周循环
            if (courseRangeDto.getCourseRangeType() == 2) {
                String[] newContent  = courseRangeDto.getCourseRangeContent().split(",");
                //需要新增的课程细节
                List<String> addContent = Arrays.asList(newContent).stream().
                        filter(content -> !Arrays.asList(oldContents).contains(content)).collect(Collectors.toList());
                List<CourseDetail> addCourseDetailList = new ArrayList<>();
                for (String s : addContent) {
                    CourseDetail courseDetail = new CourseDetail();
                    BeanUtils.copyProperties(courseRangeDto,courseDetail);
                    courseDetail.setWeek(s);
                    addCourseDetailList.add(courseDetail);
                }
                courseDetailService.saveBatch(addCourseDetailList);

                //需要删除的课程细节
                List<String> removeContent = Arrays.asList(oldContents).stream().
                        filter(content -> !Arrays.asList(newContent).contains(content)).collect(Collectors.toList());
                removeContent.forEach(content -> {
                    QueryWrapper<CourseDetail> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("course_id",courseRangeDto.getCourseId());
                    queryWrapper.eq("range_time",timeRange.getRangeTime());
                    if (courseRange.getCoachId() != null) {
                        queryWrapper.eq("coach_id",courseRange.getCoachId());
                    }
                    queryWrapper.eq("week",content);
                    CourseDetail removeDetail = courseDetailService.getOne(queryWrapper);
                    courseDetailService.removeById(removeDetail);
                });

                //需要修改的课程细节
                List<String> updateContent = Arrays.asList(newContent).stream().
                        filter(content -> !Arrays.asList(addContent).contains(content)).collect(Collectors.toList());
                updateContent.forEach(content -> {
                    LambdaUpdateWrapper<CourseDetail> updateWrapper = new LambdaUpdateWrapper<>();
                    updateWrapper.eq(CourseDetail::getCourseId,courseRangeDto.getCourseId())
                            .eq(CourseDetail::getRangeTime,timeRange.getRangeTime())
                            .eq(courseRange.getCoachId() != null,CourseDetail::getCoachId,courseRange.getCoachId())
                            .eq(CourseDetail::getWeek,content).
                            set(courseRangeDto.getCoachId() != null,CourseDetail::getCoachId,courseRangeDto.getCoachId())
                            .set(CourseDetail::getRangeTime,courseRangeDto.getRangeTime())
                            .set(CourseDetail::getRoomId,courseRangeDto.getRoomId());
                    courseDetailService.update(updateWrapper);
                });

                CourseRange newRange = new CourseRange();
                BeanUtils.copyProperties(courseRangeDto,newRange);
                return this.baseMapper.updateById(newRange) > 0;
            }
        }
        return false;
    }
}
