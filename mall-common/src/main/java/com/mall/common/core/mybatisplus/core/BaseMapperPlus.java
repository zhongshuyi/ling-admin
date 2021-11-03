package com.mall.common.core.mybatisplus.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author 钟舒艺
 * @date 2021-10-28-21:49
 **/
public interface BaseMapperPlus<T> extends BaseMapper<T> {

    /**
     * 批量插入
     * @param batchList 数据集合
     * @return 成功条数
     */
    int insertAll(@Param("list") Collection<T> batchList);

}
