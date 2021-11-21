package com.mall.common.core.mybatisplus.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.Collection;
import org.apache.ibatis.annotations.Param;

/**
 * 增强mybatisPlus Mapper.
 *
 * @param <T> 实体类
 * @author 钟舒艺
 **/
public interface BaseMapperPlus<T> extends BaseMapper<T> {

    /**
     * 批量插入.
     *
     * @param batchList 数据集合
     * @return 成功条数
     */
    int insertAll(@Param("list") Collection<T> batchList);

}
