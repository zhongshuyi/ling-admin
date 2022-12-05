package com.ling.system.convert;

import com.ling.common.core.interfaces.BaseConvert;
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.vo.SysAdminVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户转换类.
 *
 * @author 钟舒艺
 * @since 2022-10-22 11:09
 **/
@Mapper
public interface SysAdminConvert extends BaseConvert<SysAdminVO, SysAdminDTO, SysAdmin> {

    /**
     * 实例.
     */
    SysAdminConvert INSTANCT = Mappers.getMapper(SysAdminConvert.class);
}
