package com.yoga.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.system.entity.Coach;
import com.yoga.system.entity.Place;
import com.yoga.system.query.CoachPageQuery;
import com.yoga.system.query.PlacePageQuery;
import com.yoga.system.service.CoachService;
import com.yoga.system.vo.CoachPageVO;
import com.yoga.system.vo.PlacePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-09
 */
@Api(tags = "教练接口")
@RestController
@RequestMapping("/coach")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @ApiOperation(value = "教练分页列表")
    @GetMapping("/page")
    public PageResult<CoachPageVO> listCoachsWithPage(
            CoachPageQuery queryParams
    ) {
        IPage<CoachPageVO> result = coachService.listCoachsWithPage(queryParams);
        return PageResult.success(result);
    }


    @ApiOperation(value = "新增教练")
    @PostMapping
    public Result addCoach(@RequestBody Coach coach) {
        boolean result = coachService.save(coach);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改教练")
    @PutMapping
    public Result updateCoach(@RequestBody Coach coach) {
        boolean result = coachService.updateById(coach);
        return Result.judge(result);
    }


    @ApiOperation(value = "教练列表")
    @GetMapping("/list/{placeId}")
    public Result getCoachList(@ApiParam("场馆ID") @PathVariable Integer placeId) {
        List<Coach> list = coachService.listCoach(placeId);
        return Result.success(list);
    }

    @ApiOperation(value = "获取教练名字")
    @GetMapping("/one/{coachId}")
    public Result<String> getCoachName(@ApiParam("教练ID") @PathVariable Integer coachId) {
         QueryWrapper<Coach> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("coach_id",coachId);
        Coach coach = coachService.getOne(queryWrapper);
        if (coach != null) {
            return Result.success(coach.getCoachName());
        }
        return Result.success(null);
    }
}

