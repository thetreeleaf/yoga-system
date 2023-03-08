package com.yoga.member.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.common.result.PageResult;
import com.yoga.common.result.Result;
import com.yoga.common.result.ResultCode;
import com.yoga.common.web.util.MemberUtils;
import com.yoga.member.dto.MemberAuthInfoDTO;
import com.yoga.member.dto.MemberDTO;
import com.yoga.member.entity.Member;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.service.MemberService;
import com.yoga.member.vo.MemberPageVO;
import com.yoga.member.vo.MemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  会员管理
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
@Api(tags = "会员管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "会员分页列表")
    @GetMapping("/page")
    public PageResult<MemberPageVO> listMembersWithPage(
           MemberPageQuery queryParams
    ) {
        IPage<MemberPageVO> result = memberService.listMembersWithPage(queryParams);
        return PageResult.success(result);
    }


    @ApiOperation(value = "根据会员ID获取openid")
    @GetMapping("/{memberId}/openid")
    public Result<String> getMemberOpenId(
            @ApiParam("会员ID") @PathVariable Long memberId
    ) {
        Member member = memberService.getOne(
                new LambdaQueryWrapper<Member>()
                        .eq(Member::getId, memberId)
                        .select(Member::getOpenid)
        );
        String openid = member.getOpenid();
        return Result.success(openid);
    }

    @ApiOperation(value = "新增会员")
    @PostMapping
    public Result<Long> addMember(@RequestBody MemberDTO member) {
        Long memberId = memberService.addMember(member);
        return Result.success(memberId);
    }


    @ApiOperation(value = "购卡扣减会员余额")
    @PutMapping("/current/{placeId}/balances/_deduct")
    public <T> Result<T> deductBalance(@ApiParam("场馆ID") @PathVariable Integer placeId,@RequestParam(name = "balance") Long balances,Integer memberCardId) {
        Long memberId = MemberUtils.getMemberId();
        boolean result = memberService.updateBalance(placeId,memberId,balances,memberCardId);
        return Result.judge(result);
    }

    @ApiOperation(value = "会员余额充值")
    @PutMapping("/current/balances/add")
    public <T> Result<T> addBalance(@RequestParam(name = "balance") Long balances) {
        Long memberId = MemberUtils.getMemberId();
        Member member = memberService.getById(memberId);
        if (member.getBalance() != null) {
            member.setBalance(member.getBalance() + balances);
        } else {
            member.setBalance(balances);
        }
        boolean result = memberService.updateById(member);
        return Result.judge(result);
    }

    @ApiOperation(value = "会员约课扣减余额")
    @PutMapping("/order/balances/reduce")
    public <T> Result<T> reduceBalance(@RequestParam(name = "balance") Long balances) {
        Long memberId = MemberUtils.getMemberId();
        Member member = memberService.getById(memberId);
        member.setBalance(member.getBalance() - balances);
        boolean result = memberService.updateById(member);
        return Result.judge(result);
    }


    @ApiOperation(value = "获取登录会员信息")
    @GetMapping("/me")
    public Result<MemberVO> getCurrentMemberInfo() {
        MemberVO memberVO = memberService.getCurrentMemberInfo();
        return Result.success(memberVO);
    }

    @ApiOperation(value = "根据 openid 获取会员认证信息")
    @GetMapping("/openid/{openid}")
    public Result<MemberAuthInfoDTO> loadUserByOpenId(
            @ApiParam("微信身份标识") @PathVariable String openid
    ) {
        MemberAuthInfoDTO memberAuthInfo = memberService.getByOpenid(openid);
        if (memberAuthInfo == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(memberAuthInfo);
    }

    @ApiOperation(value = "获取小程序用户数量")
    @GetMapping("/num")
    public Result getMemberNum() {
        Integer num = memberService.getMemberNum();
        return Result.success(num);
    }

}

