package com.yoga.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.member.entity.MemberType;
import com.yoga.member.entity.MembershipCard;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.query.MemberTypePageQuery;
import com.yoga.member.service.MemberService;
import com.yoga.member.service.MemberTypeService;
import com.yoga.member.vo.MemberPageVO;
import com.yoga.member.vo.MemberTypePageVO;
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
 * @since 2022-02-27
 */
@Api(tags = "会员类型管理")
@RestController
@RequestMapping("/memberType")
@RequiredArgsConstructor
public class MemberTypeController {

    private final MemberTypeService memberTypeService;

    @ApiOperation(value = "会员类型分页列表")
    @GetMapping("/page")
    public PageResult<MemberTypePageVO> listMemberTypeWithPage(
            MemberTypePageQuery queryParams
    ) {
        IPage<MemberTypePageVO> result = memberTypeService.listMemberTypeWithPage(queryParams);
        return PageResult.success(result);
    }


    @ApiOperation(value = "修改会员类型")
    @PutMapping()
    public Result updateMemberType(
            @RequestBody MemberType memberType
    ) {
        boolean result = memberTypeService.updateMemberType(memberType);
        return Result.judge(result);
    }

    @ApiOperation(value = "获取会员类型")
    @GetMapping("/{placeId}/place/{memberId}")
    public Result getMemberType(@ApiParam("会员ID") @PathVariable Integer placeId,@ApiParam("会员ID") @PathVariable Integer memberId) {
        QueryWrapper<MemberType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("place_id",placeId);
        MemberType memberType = memberTypeService.getOne(queryWrapper);
        return Result.success(memberType);
    }


    @ApiOperation(value = "添加会员类型")
    @PostMapping("/add")
    public Result addMemberType(@RequestBody MemberType memberType) {
        boolean result = memberTypeService.save(memberType);
        return Result.judge(result);
    }
}

