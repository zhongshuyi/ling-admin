package com.ling.dome.convert;

import com.ling.common.core.interfaces.BaseConvert;
import com.ling.dome.dto.TestDomeDTO;
import com.ling.dome.entity.TestDome;
import com.ling.dome.vo.TestDomeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户转换类.
 *
 * @author 钟舒艺
 * @since 2022-10-22 11:09
 **/
@Mapper
public interface TestDomeConvert extends BaseConvert<TestDomeVO, TestDomeDTO, TestDome> {

    /**
     * 实例.
     */
    TestDomeConvert INSTANCT = Mappers.getMapper(TestDomeConvert.class);
}
