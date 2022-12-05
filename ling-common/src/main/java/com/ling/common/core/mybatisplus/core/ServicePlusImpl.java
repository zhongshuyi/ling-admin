package com.ling.common.core.mybatisplus.core;

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

/**
 * IServicePlus 实现类.
 *
 * @param <M> Mapper类
 * @param <E> 数据实体类
 * @param <V> vo类
 * @author 钟舒艺
 */
public class ServicePlusImpl<M extends BaseMapperPlus<E>, E, V, D>
        extends ServiceImpl<M, E>
        implements IServicePlus<E, V, D> {

    private static final long serialVersionUID = -2447762079455049677L;

    /**
     * 视图对象的class信息.
     */
    @Getter
    private final Class<V> voClass = currentVoClass();

    @Override
    protected final Class<E> currentModelClass() {
        return Convert.convert(new TypeReference<>() {
        }, ReflectionKit
                .getSuperClassGenericType(
                        this.getClass(),
                        ServicePlusImpl.class,
                        1
                ));
    }

    /**
     * 获取泛型中Vo类的class.
     *
     * @return Vo类的class
     */
    private Class<V> currentVoClass() {
        return Convert.convert(new TypeReference<>() {
        }, ReflectionKit
                .getSuperClassGenericType(
                        this.getClass(),
                        ServicePlusImpl.class,
                        2
                ));
    }

    @Override
    public final V getVoById(
            final Serializable id,
            final CopyOptions copyOptions
    ) {
        return BeanUtil.toBean(
                getBaseMapper().selectById(id),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public final List<V> listVoByIds(
            final Collection<? extends Serializable> idList,
            final CopyOptions copyOptions
    ) {
        return BeanUtil.copyToList(
                getBaseMapper().selectBatchIds(idList),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public final List<V> listVo(
            final Wrapper<E> queryWrapper,
            final CopyOptions copyOptions
    ) {
        return BeanUtil.copyToList(
                getBaseMapper().selectList(queryWrapper),
                getVoClass(),
                copyOptions
        );
    }

    /**
     * 通过DTO查询vo集合.
     *
     * @param dto 数据传输对象
     * @return voList
     */
    @Override
    public List<V> listVoByDTO(final D dto) {
        final E e = BeanUtil.toBean(dto, getEntityClass());
        return BeanUtil.copyToList(
                getBaseMapper().selectList(new QueryWrapper<>(e)),
                getVoClass(),
                new CopyOptions()
        );
    }

    @Override
    public final PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
            final D dto
    ) {
        return pageVo(
                page,
                new QueryWrapper<>(BeanUtil.toBean(dto, getEntityClass()))
        );
    }

    @Override
    public final PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
            final Wrapper<E> queryWrapper,
            final CopyOptions copyOptions
    ) {
        final PagePlus<E, V> result = getBaseMapper().selectPage(page, queryWrapper);
        result.setRecordsVo(
                BeanUtil.copyToList(
                        result.getRecords(),
                        getVoClass(),
                        copyOptions
                ));
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
    public final boolean saveAll(final Collection<E> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return false;
        }
        return baseMapper.insertAll(entityList) == entityList.size();
    }

    /**
     * 保存前的数据校验.
     *
     * @param e 实体类
     */
    @Override
    public void validEntityBeforeSave(final E e) {
        // 如果新增与更新前需要验证,则重写此方法
    }

    @Override
    public void validEntityBeforeDel(final Long id) {
        // 如果保存前需要验证或者做其他操作,则重写此方法
    }
}
