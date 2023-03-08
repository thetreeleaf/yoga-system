package com.yoga.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.system.entity.Place;
import com.yoga.system.entity.SysUser;
import com.yoga.system.entity.SysUserRole;
import com.yoga.system.mapper.PlaceMapper;
import com.yoga.system.query.PlacePageQuery;
import com.yoga.system.service.PlaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoga.system.service.SysUserService;
import com.yoga.system.vo.PlacePageVO;
import com.yoga.system.vo.UserPageVO;
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
 * @since 2022-03-05
 */
@Service
@RequiredArgsConstructor
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {


    private final SysUserService sysUserService;

    /**
     * 获取场馆分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<PlacePageVO> listPlacesWithPage(@Param("queryParams") PlacePageQuery queryParams) {
        Page<PlacePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PlacePageVO> list = this.baseMapper.listPlacesWithPage(page, queryParams);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updatePlace(Integer deleted,Place place) {
        boolean result = false;
        if (deleted == 0) {
            result = this.updateById(place);
            return result;
        }
        if (deleted == 1) {
            place.setDeleted(deleted);
            result = this.updateById(place);
            if (result) {
                List<SysUser> userList = sysUserService.list(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPlaceId, place.getId()));
                for (SysUser sysUser : userList) {
                    sysUser.setStatus(0);
                }
                result = sysUserService.updateBatchById(userList);
            }
        }
        return result;
    }

    @Override
    public Integer getPlaceNum() {
        Integer i = this.baseMapper.getPlaceNum();
        return i;
    }
}
