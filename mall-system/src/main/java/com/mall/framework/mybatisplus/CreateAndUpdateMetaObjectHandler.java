package com.mall.framework.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author 钟舒艺
 * @date 2021-10-28-15:24
 **/
@Slf4j
public class CreateAndUpdateMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间属性名
     */
    private static final  String CREATE_TIME_STR = "createTime";

    /**
     *  创建人
     */
    private static final  String CREATE_BY_STR = "createBy";

    private static final  String UPDATE_BY = "updateBy";

    private static final  String UPDATE_TIME = "updateBy";




    /**
     * 插入时填充
     * @param metaObject 操作对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            //根据属性名字设置要填充的值
            if (metaObject.hasGetter(CREATE_TIME_STR)) {
                this.setFieldValByName(CREATE_TIME_STR, new Date(), metaObject);
            }
            if (metaObject.hasGetter(CREATE_BY_STR)) {
                this.setFieldValByName(CREATE_BY_STR, getLoginUsername(), metaObject);
            }
        } catch (Exception e) {
            throw new BusinessErrorException("自动注入异常 => " + e.getMessage());
        }
        updateFill(metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (metaObject.hasGetter(UPDATE_BY)) {
                this.setFieldValByName(UPDATE_BY, getLoginUsername(), metaObject);
            }
            if (metaObject.hasGetter(UPDATE_TIME)) {
                this.setFieldValByName("updateTime", new Date(), metaObject);
            }
        } catch (Exception e) {
            throw new BusinessErrorException("自动注入异常 => " + e.getMessage());
        }
    }

    /**
     * 获取登录用户名
     */
    private String getLoginUsername() {
        AdminUserDetails loginUser;
        try {
            loginUser = SecurityUtils.getLoginUser();
        } catch (Exception e) {
            log.error("自动注入警告 => 用户未登录");
            return null;
        }
        return loginUser.getUsername();
    }
}
