package com.yoga.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.system.entity.Coach;
import com.yoga.system.entity.SysUser;
import com.yoga.system.mapper.CoachMapper;
import com.yoga.system.query.CoachPageQuery;
import com.yoga.system.service.CoachService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.system.vo.CoachPageVO;
import com.yoga.system.vo.PlacePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-09
 */
@Service
public class CoachServiceImpl extends ServiceImpl<CoachMapper, Coach> implements CoachService {

    @Override
    public IPage<CoachPageVO> listCoachsWithPage(@Param("queryParams") CoachPageQuery queryParams) {

        Page<CoachPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<CoachPageVO> list = this.baseMapper.listCoachsWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<Coach> listCoach(Integer placeId) {
        QueryWrapper<Coach> wrapper = new QueryWrapper<>();
        if (placeId != 1) {
            wrapper.eq("place_id",placeId);
        }
        List<Coach> coaches = this.baseMapper.selectList(wrapper);
        return coaches;
    }

}
