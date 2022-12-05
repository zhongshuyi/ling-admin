package com.ling.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.BaseVO;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 路由视图.
 *
 * @author 钟舒艺
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RouterVO extends BaseVO {

    /**
     * 路径.
     */
    private String path;


    /**
     * 路由名称(唯一).
     */
    private String name;

    /**
     * 组件路径.
     */
    private String component;

    /**
     * 重定向地址.
     */
    private String redirect;

    /**
     * 配置.
     */
    private RouterMeta meta;

    /**
     * 子路由.
     */
    private List<RouterVO> children;
}
