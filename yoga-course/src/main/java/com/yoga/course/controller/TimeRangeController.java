package com.yoga.course.controller;


import com.yoga.common.result.Result;
import com.yoga.course.entity.Room;
import com.yoga.course.entity.TimeRange;
import com.yoga.course.service.RoomService;
import com.yoga.course.service.TimeRangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-11
 */
@Api(tags = "时间段接口")
@RestController
@RequestMapping("/timeRange")
@RequiredArgsConstructor
public class TimeRangeController {

    private final TimeRangeService timeRangeService;

    @ApiOperation(value = "时间段列表")
    @GetMapping("/list")
    public Result getRoomRangeList() {
        List<TimeRange> list = timeRangeService.list();
        return Result.success(list);
    }

}

