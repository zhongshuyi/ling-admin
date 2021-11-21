package com.mall.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import java.util.List;
import lombok.Data;

/**
 * 路由视图.
 *
 * @author 钟舒艺
 **/
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel("路由")
public class RouterVo {

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
    private List<RouterVo> children;
}
