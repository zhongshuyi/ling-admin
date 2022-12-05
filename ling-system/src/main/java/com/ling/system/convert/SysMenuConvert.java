package com.ling.system.convert;

import com.ling.common.core.interfaces.BaseConvert;
import com.ling.system.dto.SysMenuDTO;
import com.ling.system.entity.SysMenu;
import com.ling.system.vo.RouterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 菜单Bean转化.
 *
 * @author 钟舒艺
 * @since 2022-10-26 21:09
 **/
@Mapper
public interface SysMenuConvert extends BaseConvert<RouterVO, SysMenuDTO, SysMenu> {

    /**
     * 实例.
     */
    SysMenuConvert INSTANCT = Mappers.getMapper(SysMenuConvert.class);

}
