package com.mall.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息类
 *
 * @author 钟舒艺
 * @date 2021-11-02-0:09
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = -4828260563069859319L;

    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private long total;

    /**
     * 列表数据
     */
    @ApiModelProperty("列表数据")
    private List<T> list;
}
