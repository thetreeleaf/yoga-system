package com.yoga.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yoga.system.entity.Coach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.system.query.CoachPageQuery;
import com.yoga.system.vo.CoachPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-03-09
 */
@Mapper
public interface CoachMapper extends BaseMapper<Coach> {

    List<CoachPageVO> listCoachsWithPage(@Param("page") Page<CoachPageVO> page,@Param("queryParams") CoachPageQuery queryParams);
}
