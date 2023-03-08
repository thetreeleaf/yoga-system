package com.yoga.course.controller;


import com.yoga.common.result.Result;
import com.yoga.course.entity.Room;
import com.yoga.course.service.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(tags = "教室接口")
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {


    private final RoomService roomService;

    @ApiOperation(value = "教室列表")
    @GetMapping("/list")
    public Result getRoomList() {
        List<Room> list = roomService.list();
        return Result.success(list);
    }
}

