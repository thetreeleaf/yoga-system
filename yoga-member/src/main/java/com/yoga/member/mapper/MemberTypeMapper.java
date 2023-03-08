package com.yoga.member.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.member.entity.MemberType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.query.MemberTypePageQuery;
import com.yoga.member.vo.MemberTypePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface MemberTypeMapper extends BaseMapper<MemberType> {

    List<MemberTypePageVO> listMemberTypeWithPage(@Param("page") Page<MemberTypePageVO> page,@Param("queryParams") MemberTypePageQuery queryParams);
}
