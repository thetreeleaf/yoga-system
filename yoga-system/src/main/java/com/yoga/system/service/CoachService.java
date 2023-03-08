package com.yoga.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yoga.system.entity.Coach;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yoga.system.query.CoachPageQuery;
import com.yoga.system.vo.CoachPageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-09
 */
public interface CoachService extends IService<Coach> {

    IPage<CoachPageVO> listCoachsWithPage(CoachPageQuery queryParams);

    List<Coach> listCoach(Integer placeId);
}
