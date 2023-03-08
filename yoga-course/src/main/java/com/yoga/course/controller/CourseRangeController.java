package com.yoga.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoga.common.result.Result;
import com.yoga.course.dto.CourseRangeDto;
import com.yoga.course.entity.Course;
import com.yoga.course.entity.CourseRange;
import com.yoga.course.service.CourseRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Api(tags = "课程安排接口")
@RestController
@RequestMapping("/courseRange")
@RequiredArgsConstructor
public class CourseRangeController {

    private final CourseRangeService courseRangeService;

    @ApiOperation(value = "新增课程安排")
    @PostMapping
    public Result addCourseRange(@RequestBody CourseRangeDto courseRangeDto) {
        boolean result = courseRangeService.addCourseRange(courseRangeDto);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取课程安排信息")
    @GetMapping(value = "/{courseId}")
    public Result getCourseRangeDetail(
            @ApiParam("课程ID") @PathVariable Integer courseId
    ) {

        CourseRange range = courseRangeService.getCourseRangeDetail(courseId);
        return Result.success(range);
    }

    @ApiOperation(value = "修改课程安排信息")
    @PutMapping(value = "/{courseRangeId}")
    public Result updateCourseRange(
            @ApiParam("课程安排ID") @PathVariable Integer courseRangeId,
            @RequestBody CourseRangeDto courseRangeDto
    ) {
        System.out.println("课程安排ID" + courseRangeId);
        System.out.println(courseRangeDto.toString());
        boolean result = courseRangeService.updateCourseRange(courseRangeId,courseRangeDto);
        return Result.judge(result);
    }
}

