package com.ling.common.core.validate;

/**
 * 分组校验类.
 *
 * @author 钟舒艺
 **/
public class ValidationGroups {

    /**
     * 添加时校验.
     */
    public interface Add {
    }

    /**
     * 编辑时校验.
     */
    public interface Edit {
    }

    /**
     * 查询时校验.
     */
    public interface Query {
    }

    /**
     * 添加时校验标识.
     */
    public static final Class<ValidationGroups.Add> ADD = ValidationGroups.Add.class;

    /**
     * 修改时校验标识.
     */
    public static final Class<ValidationGroups.Edit> EDIT = ValidationGroups.Edit.class;

    /**
     * 查询时校验标识.
     */
    public static final Class<ValidationGroups.Query> QUERY = ValidationGroups.Query.class;

}
