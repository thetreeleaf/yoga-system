package com.yoga.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.member.entity.MemberType;
import com.yoga.member.mapper.MemberTypeMapper;
import com.yoga.member.query.MemberPageQuery;
import com.yoga.member.query.MemberTypePageQuery;
import com.yoga.member.service.MemberTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.member.vo.MemberTypePageVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

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

public class MemberTypeServiceImpl extends ServiceImpl<MemberTypeMapper, MemberType> implements MemberTypeService {



    @Override
    public IPage<MemberTypePageVO> listMemberTypeWithPage(@Param("queryParams") MemberTypePageQuery queryParams) {
        Page<MemberTypePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<MemberTypePageVO> list = this.baseMapper.listMemberTypeWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updateMemberType(MemberType memberType) {
//        Integer memberId = memberType.getMemberId();
        QueryWrapper<MemberType> queryWrapper = new QueryWrapper<MemberType>();
        queryWrapper.eq("member_id",memberType.getMemberId());
        queryWrapper.eq("place_id",memberType.getPlaceId());
        MemberType memberType1 = this.baseMapper.selectOne(queryWrapper);
        if (memberType1 == null) {
            int insert = this.baseMapper.insert(memberType);
            return insert > 0 ? true : false;
        }
        UpdateWrapper<MemberType> wrapper = new UpdateWrapper<>();
        wrapper.eq("member_type_id",memberType1.getMemberTypeId()).
                set("member_type_name",memberType.getMemberTypeName()).
                set("start_time",memberType.getStartTime()).
                set("end_time",memberType.getEndTime()).
                set("member_id",memberType.getMemberId()).
                set("member_card_id",memberType.getMemberCardId()).
                set("place_id",memberType.getPlaceId());
        return this.baseMapper.update(null, wrapper) > 0 ? true : false;

    }
}
