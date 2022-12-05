package com.ling.system.convert;

import com.ling.common.core.interfaces.BaseConvert;
import com.ling.system.dto.SysRoleDTO;
import com.ling.system.entity.SysRole;
import com.ling.system.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-26 21:55
 **/
@Mapper
public interface SysRoleConvert extends BaseConvert<SysRoleVO, SysRoleDTO, SysRole> {

    /**
     * 实例.
     */
    SysRoleConvert INSTANCT = Mappers.getMapper(SysRoleConvert.class);
}
