package com.yoga.course.controller;



import com.yoga.common.result.Result;
import com.yoga.course.entity.CourseDetail;
import com.yoga.course.query.CourseDetailQuery;
import com.yoga.course.service.CourseDetailService;
import com.yoga.course.vo.CourseDetailVO;
import com.yoga.course.vo.CourseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "课程细节接口")
@RestController
@RequestMapping("/courseDetail")
@RequiredArgsConstructor
public class CourseDetailController {


    private final CourseDetailService courseDetailService;

    @ApiOperation(value = "小程序端课程细节列表")
    @GetMapping("/{placeId}/list")
    public Result<List<CourseDetailVO>> listPlaceCourse(
            @ApiParam("场馆ID") @PathVariable Integer placeId,
            CourseDetailQuery queryParams
    ) throws Throwable {
        List<CourseDetailVO>  courseDetailVOList = courseDetailService.listPlaceCourse(placeId,queryParams);
        return Result.success(courseDetailVOList);
    }

    @ApiOperation(value = "后台课表")
    @GetMapping("/{placeId}/table/{data}")
    public Result<List<CourseVO>> PlaceCourseTable(
            @ApiParam("场馆ID") @PathVariable Integer placeId,@PathVariable Integer data
    ) {
        List<CourseVO> courseVOS = courseDetailService.PlaceCourseTable(placeId,data);
        return Result.success(courseVOS);
    }

    @ApiOperation(value = "修改课程细节")
    @PutMapping(value = "/{courseDetailId}")
    public Result updateCourseDetail(
            @ApiParam("课程细节ID") @PathVariable Integer courseDetailId,
            @RequestBody CourseDetail CourseDetail
    ) {
        CourseDetail detail = courseDetailService.getById(courseDetailId);
        BeanUtils.copyProperties(CourseDetail,detail);
        boolean result = courseDetailService.updateById(detail);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除课程细节")
    @DeleteMapping("/{id}")
    public Result deleteCourseDetail(
            @ApiParam("课程细节ID") @PathVariable Integer id
    ) {
        boolean status = courseDetailService.removeById(id);
        return Result.judge(status);
    }

    @ApiOperation(value = "获取没有空闲的教练id集合")
    @GetMapping("/{placeId}/idlist")
    public Result<List<Integer>> idlist(
            @ApiParam("场馆ID") @PathVariable Integer placeId,
            CourseDetailQuery queryParams
    ) throws Throwable {
        List<CourseDetailVO>  courseDetailVOList = courseDetailService.listPlaceCourse(placeId,queryParams);
        for (CourseDetailVO courseDetailVO : courseDetailVOList) {
            System.out.println(courseDetailVO.toString());
        }
        List<Integer> integerList = courseDetailVOList.stream().filter(item -> item.getCoachId() != null)
                .map(item -> item.getCoachId()).collect(Collectors.toList());
        System.out.println(integerList.size());
        return Result.success(integerList);
    }
}

