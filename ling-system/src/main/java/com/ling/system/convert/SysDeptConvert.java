package com.ling.system.convert;

import com.ling.common.core.domain.model.SysDept;
import com.ling.common.core.interfaces.BaseConvert;
import com.ling.system.dto.SysDeptDTO;
import com.ling.system.vo.SysDeptVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-26 21:53
 **/
@Mapper
public interface SysDeptConvert extends BaseConvert<SysDeptVO, SysDeptDTO, SysDept> {
    /**
     * 实例.
     */
    SysDeptConvert INSTANCT = Mappers.getMapper(SysDeptConvert.class);
}
