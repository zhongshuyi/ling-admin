package com.mall.common.core.mybatisplus.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IServicePlus 实现类.
 *
 * @param <M> Mapper类
 * @param <T> 数据实体类
 * @param <V> vo类
 * @author 钟舒艺
 */
@SuppressWarnings("unused")
public class ServicePlusImpl<M extends BaseMapperPlus<T>, T, V>
        extends ServiceImpl<M, T>
        implements IServicePlus<T, V> {

    private static final long serialVersionUID = -2447762079455049677L;
    /**
     * mapper 的class信息.
     */
    @Getter
    private final Class<M> mapperClass = currentMapperClass();
    /**
     * 视图对象的class信息.
     */
    @Getter
    private final Class<V> voClass = currentVoClass();
    /**
     * 注入mybatisPlus的mapper层操作对象.
     */
    @Autowired(required = false)
    private M baseMapper;

    /**
     * 获取baseMapper.
     *
     * @return 获取baseMapper.
     */
    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    @Override
    protected final Class<M> currentMapperClass() {
        return Convert.convert(new TypeReference<Class<M>>() {
        }, ReflectionKit
                .getSuperClassGenericType(
                        this.getClass(),
                        ServicePlusImpl.class,
                        0));
    }

    @Override
    protected final Class<T> currentModelClass() {
        return Convert.convert(new TypeReference<Class<T>>() {
        }, ReflectionKit
                .getSuperClassGenericType(
                        this.getClass(),
                        ServicePlusImpl.class,
                        1));
    }

    /**
     * 获取泛型中Vo类的class.
     *
     * @return Vo类的class
     */
    protected final Class<V> currentVoClass() {
        return Convert.convert(new TypeReference<Class<V>>() {
        }, ReflectionKit
                .getSuperClassGenericType(
                        this.getClass(),
                        ServicePlusImpl.class,
                        2));
    }

    @Override
    public final V getVoById(
            final Serializable id,
            final CopyOptions copyOptions) {
        return BeanUtil.toBean(
                getBaseMapper().selectById(id),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public final List<V> listVoByIds(
            final Collection<? extends Serializable> idList,
            final CopyOptions copyOptions) {
        return BeanUtil.copyToList(
                getBaseMapper().selectBatchIds(idList),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public final List<V> listVo(
            final Wrapper<T> queryWrapper,
            final CopyOptions copyOptions
    ) {
        return BeanUtil.copyToList(
                getBaseMapper().selectList(queryWrapper),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public final <B> PagePlus<T, V> pageVo(final PagePlus<T, V> page, final B bo) {
        return pageVo(
                page,
                new QueryWrapper<>(BeanUtil.toBean(bo, getEntityClass())));
    }

    @Override
    public final PagePlus<T, V> pageVo(
            final PagePlus<T, V> page,
            final Wrapper<T> queryWrapper,
            final CopyOptions copyOptions
    ) {
        PagePlus<T, V> result = getBaseMapper().selectPage(page, queryWrapper);
        result.setRecordsVo(
                BeanUtil.copyToList(
                        result.getRecords(),
                        getVoClass(),
                        copyOptions));
        return result;
    }

    @Override
    public final List<V> listVoByMap(
            final Map<String, Object> columnMap,
            final CopyOptions copyOptions
    ) {
        return BeanUtil.copyToList(
                getBaseMapper().selectByMap(columnMap),
                getVoClass(),
                copyOptions
        );
    }


    @Override
    public final boolean saveAll(final Collection<T> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return false;
        }
        return baseMapper.insertAll(entityList) == entityList.size();
    }

    /**
     * 保存前的数据校验.
     *
     * @param t 实体类
     */
    @Override
    public void validEntityBeforeSave(final T t) {

    }

    @Override
    public void validEntityBeforeDel(final Long id) {

    }
}
