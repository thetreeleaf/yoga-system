package com.yoga.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yoga.common.result.Result;
import com.yoga.member.entity.MembershipCard;
import com.yoga.member.query.MembershipCardQuery;
import com.yoga.member.service.MembershipCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  会员卡管理
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
@Api(tags = "会员卡管理")
@RestController
@RequestMapping("/membershipCard")
@RequiredArgsConstructor
public class MembershipCardController {

    private final MembershipCardService membershipCardService;

    @ApiOperation(value = "会员卡列表")
    @GetMapping("/list")
    public Result<List<MembershipCard>> list(MembershipCardQuery membershipCardQuery) {
        QueryWrapper<MembershipCard> queryWrapper = new QueryWrapper<MembershipCard>();
        List<MembershipCard> list = new ArrayList<>();
        if (membershipCardQuery.getPlaceId() == 1) {
            list = membershipCardService.list();
            return Result.success(list);
        }
        queryWrapper.eq("place_id",membershipCardQuery.getPlaceId());
        if (membershipCardQuery.getName() == null) {
            list = membershipCardService.list(queryWrapper);
            return Result.success(list);
        }
        queryWrapper.like("membership_card_name",membershipCardQuery.getName());
        list = membershipCardService.list(queryWrapper);
        return Result.success(list);
    }

    @ApiOperation(value = "添加会员卡")
    @PostMapping
    public Result addMembershipCard(@RequestBody MembershipCard membershipCard) {
        boolean result = membershipCardService.save(membershipCard);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改会员卡")
    @PutMapping()
    public Result updateMembershipCard(
            @RequestBody MembershipCard membershipCard
    ) {
        boolean result = membershipCardService.updateById(membershipCard);
        return Result.judge(result);
    }


    @ApiOperation(value = "删除会员卡")
    @DeleteMapping()
    public Result deleteMembershipCard(
            @ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = membershipCardService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }

}

