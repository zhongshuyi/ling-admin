package com.ling.framework.mybatisplus.handler;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ling.common.core.domain.model.User;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.utils.SecurityUtils;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * mybatisPlus 更新与新增时.
 *
 * @author 钟舒艺
 **/
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间属性名.
     */
    private static final String CREATE_TIME_STR = "createTime";

    /**
     * 创建人.
     */
    private static final String CREATE_BY_STR = "createBy";

    /**
     * 更新人.
     */
    private static final String UPDATE_BY = "updateBy";

    /**
     * 更新时间.
     */
    private static final String UPDATE_TIME = "updateTime";


    /**
     * 插入时填充.
     *
     * @param metaObject 操作对象
     */
    @Override
    public void insertFill(final MetaObject metaObject) {
        try {
            //根据属性名字设置要填充的值
            if (metaObject.hasGetter(CreateAndUpdateMetaObjectHandler.CREATE_TIME_STR)) {
                this.setFieldValByName(CreateAndUpdateMetaObjectHandler.CREATE_TIME_STR, new Date(), metaObject);
            }
            if (metaObject.hasGetter(CreateAndUpdateMetaObjectHandler.CREATE_BY_STR)) {
                this.setFieldValByName(CreateAndUpdateMetaObjectHandler.CREATE_BY_STR, getLoginUsername(), metaObject);
            }
        } catch (final Exception e) {
            throw new BusinessErrorException(
                    HttpStatus.HTTP_INTERNAL_ERROR,
                    "自动注入异常 => " + e.getMessage(),
                    e
            );
        }
        updateFill(metaObject);
    }

    @Override
    public final void updateFill(final MetaObject metaObject) {
        try {
            if (metaObject.hasGetter(CreateAndUpdateMetaObjectHandler.UPDATE_BY)) {
                this.setFieldValByName(CreateAndUpdateMetaObjectHandler.UPDATE_BY, getLoginUsername(), metaObject);
            }
            if (metaObject.hasGetter(CreateAndUpdateMetaObjectHandler.UPDATE_TIME)) {
                this.setFieldValByName(CreateAndUpdateMetaObjectHandler.UPDATE_TIME, new Date(), metaObject);
            }
        } catch (final Exception e) {
            throw new BusinessErrorException(
                    HttpStatus.HTTP_INTERNAL_ERROR,
                    "自动注入异常 => " + e.getMessage(),
                    e
            );
        }
    }

    /**
     * 获取登录用户名.
     *
     * @return 登录用户名
     */
    private String getLoginUsername() {
        final User loginUser;
        try {
            loginUser = SecurityUtils.getLoginUser().getUser();
        } catch (final Exception e) {
            CreateAndUpdateMetaObjectHandler.log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return loginUser.getUsername();
    }
}
