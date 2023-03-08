package com.yoga.member.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.member.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.vo.MemberPageVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {


    List<MemberPageVO> listMembersWithPage(@Param("page") Page<MemberPageVO> page,@Param("queryParams") MemberPageQuery queryParams);

    Integer getMemberNum();
}
