package com.ling.common.core.domain.base;

import java.io.Serializable;
import lombok.Data;

/**
 * 公共分页查询.
 *
 * @author 钟舒艺
 **/
@Data
public class BasePageQuery implements Serializable {

    private static final long serialVersionUID = 6745507447447938043L;
    /**
     * 分页大小.
     */
    private Integer pageSize;

    /**
     * 当前页数.
     */
    private Integer pageNum;

    /**
     * 排序列.
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc.
     */
    private String isAsc;

}
