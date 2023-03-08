package com.yoga.course.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.course.entity.Course;
import com.yoga.course.query.CoursePageQuery;
import com.yoga.course.service.CourseService;
import com.yoga.course.vo.CoursePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Api(tags = "课程接口")
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {


    private final CourseService courseService;

    @ApiOperation(value = "课程分页列表")
    @GetMapping("/page")
    public PageResult<CoursePageVO> listCourseWithPage(
            CoursePageQuery queryParams
    ) {
        IPage<CoursePageVO> result = courseService.listCourseWithPage(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "新增课程")
    @PostMapping
    public Result addCourse(@RequestBody Course course) {
        boolean result = courseService.save(course);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改课程")
    @PutMapping(value = "/{courseId}")
    public Result updateCourse(
            @ApiParam("用户ID") @PathVariable Integer courseId,
            @RequestBody Course course
    ) {
        boolean result = courseService.updateCourse(courseId,course);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("/{ids}")
    public Result deleteCourse(
            @ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = courseService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }
}

