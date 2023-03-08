package com.yoga.course.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.course.entity.CourseState;
import com.yoga.course.query.CoursePageQuery;
import com.yoga.course.query.CourseStatePageQuery;
import com.yoga.course.service.CourseService;
import com.yoga.course.service.CourseStateService;
import com.yoga.course.vo.CoursePageVO;
import com.yoga.course.vo.CourseStatePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Api(tags = "课程状态接口")
@RestController
@RequestMapping("/courseState")
@RequiredArgsConstructor
public class CourseStateController {


    private final CourseStateService courseStateService;

    @ApiOperation(value = "课程状态分页列表")
    @GetMapping("/page")
    public PageResult<CourseStatePageVO> listCourseStateWithPage(
            CourseStatePageQuery queryParams
    ) {
        IPage<CourseStatePageVO> result = courseStateService.listCourseStateWithPage(queryParams);
        return PageResult.success(result);
    }


    @ApiOperation(value = "修改课程状态")
    @PutMapping("/{ids}")
    public Result updateCourseState(
            @ApiParam(" 课程状态ID，多个以英文逗号(,)分割") @PathVariable String ids,@Param("state") Integer state
    ) {
        String[] idList = ids.split(",");
        List<CourseState> courseStateList = new ArrayList<>();
        for (String s : idList) {
            CourseState courseState = courseStateService.getById(Integer.parseInt(s));
            courseState.setState(state);
            courseStateList.add(courseState);
        }
        boolean status = courseStateService.updateBatchById(courseStateList);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除课程状态")
    @DeleteMapping("/{id}")
    public Result deleteCourse(
            @ApiParam("课程状态ID，多个以英文逗号(,)分割") @PathVariable String id
    ) {
        boolean status = courseStateService.removeById(id);
        return Result.judge(status);
    }

    @ApiOperation(value = "预约")
    @PostMapping("/order")
    public Result order(@RequestBody CourseState courseState) {

        boolean result = courseStateService.save(courseState);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取课程状态id")
    @PostMapping("/getId")
    public Result<Integer> getCourseStateId(@RequestBody CourseState courseState) {
        QueryWrapper<CourseState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("detail_id",courseState.getDetailId());
        queryWrapper.eq("user_id",courseState.getUserId());
        queryWrapper.eq("state",courseState.getState());
        queryWrapper.eq("course_id",courseState.getCourseId());
        queryWrapper.eq("coach_id",courseState.getCoachId());
        CourseState state = courseStateService.getOne(queryWrapper);
        return Result.success(state.getId());
    }
}

