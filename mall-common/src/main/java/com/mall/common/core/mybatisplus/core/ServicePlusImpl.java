package com.mall.common.core.mybatisplus.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * IServicePlus 实现类
 *
 * @param <M> Mapper类
 * @param <T> 数据实体类
 * @param <V> vo类
 * @author 钟舒艺
 * @date 2021-11-02-14:42
 */
public class ServicePlusImpl<M extends BaseMapperPlus<T>, T, V> extends ServiceImpl<M, T> implements IServicePlus<T, V> {

    @Autowired
    protected M baseMapper;

    @Getter
    protected Class<M> mapperClass = currentMapperClass();

    @Getter
    protected Class<V> voClass = currentVoClass();

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 获取泛型Mapper类的class
     *
     * @return Mapper类的class
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Class<M> currentMapperClass() {
        return (Class<M>) ReflectionKit.getSuperClassGenericType(this.getClass(), ServicePlusImpl.class, 0);
    }

    /**
     * 获取泛型中数据实体类的class
     *
     * @return 数据实体类的class
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), ServicePlusImpl.class, 1);
    }

    /**
     * 获取泛型中Vo类的class
     *
     * @return Vo类的class
     */
    @SuppressWarnings("unchecked")
    protected Class<V> currentVoClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(this.getClass(), ServicePlusImpl.class, 2);
    }

    @Override
    public V getVoById(Serializable id, CopyOptions copyOptions) {
        return BeanUtil.toBean(
                getBaseMapper().selectById(id),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public List<V> listVoByIds(Collection<? extends Serializable> idList, CopyOptions copyOptions) {
        return BeanUtil.copyToList(
                getBaseMapper().selectBatchIds(idList),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public List<V> listVo(Wrapper<T> queryWrapper, CopyOptions copyOptions) {
        return BeanUtil.copyToList(
                getBaseMapper().selectList(queryWrapper),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public PagePlus<T, V> pageVo(PagePlus<T, V> page, Wrapper<T> queryWrapper, CopyOptions copyOptions) {
        PagePlus<T, V> result = getBaseMapper().selectPage(page, queryWrapper);
        result.setRecordsVo(BeanUtil.copyToList(result.getRecords(), getVoClass(), copyOptions));
        return result;
    }

    @Override
    public List<V> listVoByMap(Map<String, Object> columnMap, CopyOptions copyOptions) {
        return BeanUtil.copyToList(
                getBaseMapper().selectByMap(columnMap),
                getVoClass(),
                copyOptions
        );
    }

    @Override
    public boolean saveAll(Collection<T> entityList) {
        if (CollUtil.isEmpty(entityList)) {
            return false;
        }
        return baseMapper.insertAll(entityList) == entityList.size();
    }


    @Override
    public void validEntityBeforeSave(T t) {

    }

    @Override
    public void validEntityBeforeDel(Long id) {

    }
}
