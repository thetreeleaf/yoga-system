package com.yoga.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.system.entity.Place;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.query.PlacePageQuery;
import com.yoga.system.vo.PlacePageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-05
 */
public interface PlaceService extends IService<Place> {

    IPage<PlacePageVO> listPlacesWithPage(PlacePageQuery queryParams);

    boolean updatePlace(Integer deleted,Place place);

    Integer getPlaceNum();
}
