package com.yoga.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.common.constant.GlobalConstants;
import com.yoga.common.web.util.MemberUtils;
import com.yoga.member.dto.MemberAuthInfoDTO;
import com.yoga.member.dto.MemberDTO;
import com.yoga.member.entity.Member;
import com.yoga.member.entity.MemberType;
import com.yoga.member.entity.MembershipCard;
import com.yoga.member.mapper.MemberMapper;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.member.service.MemberTypeService;
import com.yoga.member.service.MembershipCardService;
import com.yoga.member.vo.MemberPageVO;
import com.yoga.member.vo.MemberVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Autowired
    private MemberTypeService memberTypeService;

    @Autowired
    private MembershipCardService membershipCardService;


    @Override
    public IPage<MemberPageVO> listMembersWithPage(@Param("queryParams") MemberPageQuery queryParams) {
        Page<MemberPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<MemberPageVO> list = this.baseMapper.listMembersWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }


    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    @Override
    public MemberAuthInfoDTO getByOpenid(String openid) {
        Member member = this.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getOpenid, openid)
                .select(Member::getId,
                        Member::getOpenid,
                        Member::getStatus
                )
        );
        MemberAuthInfoDTO memberAuth = null;
        if (member != null) {
            memberAuth = new MemberAuthInfoDTO()
                    .setMemberId(member.getId())
                    .setUsername(member.getOpenid())
                    .setStatus(member.getStatus());
        }
        return memberAuth;
    }



    /**
     * 新增会员
     *
     * @param memberDTO
     * @return
     */
    @Override
    public Long addMember(MemberDTO memberDTO) {
        Member umsMember = new Member();
        BeanUtil.copyProperties(memberDTO, umsMember);
        umsMember.setStatus(GlobalConstants.STATUS_YES);

        boolean result = this.save(umsMember);
        Assert.isTrue(result, "新增会员失败");
//        MemberType memberType = new MemberType();
//        memberType.setMemberTypeName("普通会员");
//        Date date = new Date();
//        Instant instant = date.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
//        memberType.setStartTime(localDateTime);
//        memberType.setMemberId(umsMember.getId().intValue());
//        memberType.setMemberCardId(5);
//        memberTypeService.save(memberType);
        return umsMember.getId();
    }

    /**
     * 获取登录会员信息
     *
     * @return
     */
    @Override
    public MemberVO getCurrentMemberInfo() {
        Long memberId = MemberUtils.getMemberId();
        Member umsMember = this.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getId, memberId)
                .select(Member::getId,
                        Member::getNickName,
                        Member::getAvatarUrl,
                        Member::getMobile,
                        Member::getBalance
                )
        );
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(umsMember, memberVO);
        return memberVO;
    }

    /**
     * 扣减会员余额
     *
     * @param placeId
     * @param memberId
     * @param balances
     * @param memberCardId
     * @return
     */
    @Override
    public boolean  updateBalance(Integer placeId, Long memberId, Long balances, Integer memberCardId) {
        Member member = this.baseMapper.selectById(memberId);
        member.setBalance(member.getBalance() - balances);
        int update = this.baseMapper.updateById(member);
//        System.out.println(member.toString());
        QueryWrapper<MemberType> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("place_id",placeId);
        MemberType memberType = memberTypeService.getOne(wrapper);
        if (update > 0) {
            QueryWrapper<MembershipCard> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("place_id",placeId);
            List<MembershipCard> cards = membershipCardService.list(queryWrapper);
            LocalDateTime startTime = getFetureDate(0);
            memberType.setStartTime(startTime);
            memberType.setMemberCardId(memberCardId);
            for (MembershipCard card : cards) {
                if (card.getMembershipCardId().equals(memberCardId)) {
                    String memberTypeName = card.getMembershipCardName().substring(0,2) + "会员";
                    memberType.setMemberTypeName(memberTypeName);
                    if (card.getMembershipCardName().equals("周卡")) {
                        memberType.setEndTime(getFetureDate(7));
                    }
                    if (card.getMembershipCardName().equals("月卡")) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        memberType.setEndTime(getFetureDate(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)));
                    }
                    if (card.getMembershipCardName().equals("年卡")) {
                        memberType.setEndTime(getFetureDate(365));
                    }
                    break;
                }
            }
        }
//        System.out.println(memberType.toString());
        boolean result = memberTypeService.updateById(memberType);
        return result;
    }

    @Override
    public Integer getMemberNum() {
        Integer i = this.baseMapper.getMemberNum();
        return i;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    private  LocalDateTime getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        Instant instant = today.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime time = instant.atZone(zoneId).toLocalDateTime();
        return time;
    }
}
