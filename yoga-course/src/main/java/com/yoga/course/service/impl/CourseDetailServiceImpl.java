package com.yoga.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoga.course.entity.CourseDetail;
import com.yoga.course.entity.CourseState;
import com.yoga.course.entity.TimeRange;
import com.yoga.course.mapper.CourseDetailMapper;
import com.yoga.course.query.CourseDetailQuery;
import com.yoga.course.service.CourseDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.course.service.CourseStateService;
import com.yoga.course.service.TimeRangeService;
import com.yoga.course.vo.CourseDetailVO;
import com.yoga.course.vo.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
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
public class CourseDetailServiceImpl extends ServiceImpl<CourseDetailMapper, CourseDetail> implements CourseDetailService {

    @Autowired
    private TimeRangeService timeRangeService;

    @Autowired
    private CourseStateService courseStateService;
    
    @Override
    public List<CourseDetailVO> listPlaceCourse(Integer placeId, CourseDetailQuery queryParams) throws Throwable {
        List<CourseDetailVO> courseDetailVOList = this.baseMapper.listPlaceCourse(placeId,queryParams);
        Date date = new Date();
        Long currentStamp = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDay = dateFormat.format(date);
        String currentDate = currentDay + " " + "00:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
        String week = dayForWeek(currentDate);
        for (CourseDetailVO courseDetailVO : courseDetailVOList) {
            //先判断是否预约了课程
            QueryWrapper<CourseState> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",queryParams.getMemberId());
            wrapper.eq("detail_id",courseDetailVO.getId());
            wrapper.eq("state",3);
            wrapper.eq("course_id",courseDetailVO.getCourseId());
            List<CourseState> list = courseStateService.list(wrapper);
            if(list.size() > 0) {
                courseDetailVO.setButtonText("取消预约");
                courseDetailVO.setDisable("false");
            } else if (courseDetailVO.getWeek().equals(week) || courseDetailVO.getWeek().equals(currentDate)) {
                String rangTime = "0" + courseDetailVO.getRangeTime().substring(0,4) + ":00";
                String stringStamp = currentDay + " " + rangTime;
                Date stringDate = format.parse(stringStamp);
                long time = stringDate.getTime();
                if (time - 60 * 60 * 1000 > currentStamp) {
                    courseDetailVO.setButtonText("预约");
                    courseDetailVO.setDisable("false");
                } else {
                    courseDetailVO.setButtonText("已过期");
                    courseDetailVO.setDisable("true");
                }
            } else {
                courseDetailVO.setButtonText("预约");
                courseDetailVO.setDisable("false");
            }
        }
        return courseDetailVOList;
    }

    @Override
    public List<CourseVO> PlaceCourseTable(Integer placeId, Integer data) {
        String[] weeks = {"周一","周二","周三","周四","周五","周六","周日"};
        String[] times = getweek(data, new Date());  //获取周日期
        List<TimeRange> list = timeRangeService.list();
        List<CourseVO> courseVOList = new ArrayList<>();
        List<CourseDetailVO> courseDetailVOList = this.baseMapper.getCourse(weeks,placeId,times);
        for (int i = 0; i < list.size(); i++) {
            TimeRange timeRange = list.get(i);
            CourseVO courseVO = new CourseVO();
            courseVO.setRangeTime(timeRange.getRangeTime());
            int index = 0;
            //获取周一的该时间段的所有课程
            List<CourseDetailVO> monday = courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index]) || courseDetailVO.getWeek().equals(times[index]))
                    .collect(Collectors.toList());
            courseVO.setMonday(monday);
            //获取周二的该时间段的所有课程
            List<CourseDetailVO> tuesday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 1]) || courseDetailVO.getWeek().equals(times[index + 1]))
                    .collect(Collectors.toList());
            courseVO.setTuesday(tuesday);
            //获取周三的该时间段的所有课程
            List<CourseDetailVO> wednesday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 2]) || courseDetailVO.getWeek().equals(times[index + 2]))
                    .collect(Collectors.toList());
            courseVO.setWednesday(wednesday);
            //获取周四的该时间段的所有课程
            List<CourseDetailVO> thursday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 3]) || courseDetailVO.getWeek().equals(times[index + 3]))
                    .collect(Collectors.toList());
            courseVO.setThursday(thursday);
            //获取周五的该时间段的所有课程
            List<CourseDetailVO> friday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 4]) || courseDetailVO.getWeek().equals(times[index + 4]))
                    .collect(Collectors.toList());
            courseVO.setFriday(friday);
            //获取周六的该时间段的所有课程
            List<CourseDetailVO> saturday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 5]) || courseDetailVO.getWeek().equals(times[index + 5]))
                    .collect(Collectors.toList());
            courseVO.setSaturday(saturday);
            //获取周日的该时间段的所有课程
            List<CourseDetailVO> sunday =  courseDetailVOList.stream()
                    .filter(courseDetailVO -> courseDetailVO.getRangeTime().equals(timeRange.getRangeTime()))
                    .filter(courseDetailVO -> courseDetailVO.getWeek().equals(weeks[index + 6]) || courseDetailVO.getWeek().equals(times[index + 6]))
                    .collect(Collectors.toList());
            courseVO.setSunday(sunday);
            courseVO.setDate(times[i]);
            courseVOList.add(courseVO);
        }
        return courseVOList;
    }

    /**
     * 获取当前周的所有日期
     * @param n -1代表上一周 +1代表下一周
     * @param time
     * @return
     */
    private static String[] getweek(int n, Date time) {

        String[] date = new String[7];
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            //获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, (cal.getFirstDayOfWeek() - day + 7 * n));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
            String s = sdf.format(cal.getTime());
            Date parse = sdf.parse(s);
            date[0] = dateFormat.format(parse);
            for (int i = 1; i < 7; i++) {
                cal.add(Calendar.DATE, 1);
                String s1 = sdf.format(cal.getTime());
                Date d = sdf.parse(s1);
                date[i] = dateFormat.format(d);
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return date;
    }


    /**
     * 获取当前日期是周几
     * @param pTime
     * @return
     * @throws Throwable
     */
    private String dayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date tmpDate = format.parse(pTime);

        Calendar cal = Calendar.getInstance();

        String[] weekDays = {"周日","周一","周二","周三","周四","周五","周六"};

        try {

            cal.setTime(tmpDate);

        } catch (Exception e) {

            e.printStackTrace();

        }

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。

        if (w < 0) {
            w = 0;
        }

        return weekDays[w];

    }

}
