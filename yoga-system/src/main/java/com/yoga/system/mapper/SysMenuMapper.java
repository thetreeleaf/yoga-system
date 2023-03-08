package com.yoga.system.mapper;

import com.yoga.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoga.system.vo.ServiceNameVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author Jisoo
 * @since 2022-02-26
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> listRoutes();

    List<ServiceNameVo> getMicroService();
}
