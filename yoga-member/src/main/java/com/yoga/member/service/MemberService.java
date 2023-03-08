package com.yoga.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.member.dto.MemberAuthInfoDTO;
import com.yoga.member.dto.MemberDTO;
import com.yoga.member.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.vo.MemberPageVO;
import com.yoga.member.vo.MemberVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
public interface MemberService extends IService<Member> {

    IPage<MemberPageVO> listMembersWithPage(@Param("queryParams") MemberPageQuery queryParams);

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    MemberAuthInfoDTO getByOpenid(String openid);


    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    Long addMember(MemberDTO member);

    /**
     * 获取登录会员信息
     *
     * @return
     */
    MemberVO getCurrentMemberInfo();

    /**
     * 扣减会员余额
     *
     * @param placeId
     * @param memberId
     * @param balances
     * @param memberCardId
     * @return
     */
    boolean updateBalance(Integer placeId, Long memberId, Long balances, Integer memberCardId);

    Integer getMemberNum();
}
