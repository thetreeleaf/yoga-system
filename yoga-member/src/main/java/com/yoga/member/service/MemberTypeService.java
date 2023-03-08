package com.yoga.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.member.entity.MemberType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.query.MemberTypePageQuery;
import com.yoga.member.vo.MemberTypePageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-27
 */
public interface MemberTypeService extends IService<MemberType> {

    IPage<MemberTypePageVO> listMemberTypeWithPage(@Param("queryParams") MemberTypePageQuery queryParams);

    boolean updateMemberType(MemberType memberType);
}
