package com.yoga.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.system.entity.Place;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.system.query.PlacePageQuery;
import com.yoga.system.vo.PlacePageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-05
 */
@Mapper
public interface PlaceMapper extends BaseMapper<Place> {

    List<PlacePageVO> listPlacesWithPage(@Param("page") Page<PlacePageVO> page,@Param("queryParams") PlacePageQuery queryParams);

    Integer getPlaceNum();
}
