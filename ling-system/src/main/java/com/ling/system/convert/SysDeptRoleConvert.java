package com.ling.system.convert;

import com.ling.common.core.interfaces.BaseConvert;
import com.ling.system.dto.SysDeptRoleDTO;
import com.ling.system.entity.SysDeptRole;
import com.ling.system.vo.SysDeptRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-26 21:49
 **/
@Mapper
public interface SysDeptRoleConvert extends BaseConvert<SysDeptRoleVO, SysDeptRoleDTO, SysDeptRole> {

    /**
     * 实例.
     */
    SysDeptRoleConvert INSTANCT = Mappers.getMapper(SysDeptRoleConvert.class);
}
