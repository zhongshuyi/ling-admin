package com.ling.common.core.mybatisplus.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 自定义 Service 接口, 实现 数据库实体与 vo 对象转换返回.
 *
 * @param <T> 实体类
 * @param <V> Vo视图类
 * @author 钟舒艺
 */
public interface IServicePlus<T, V> extends IService<T>, Serializable {


    /**
     * 根据id获取Vo对象.
     *
     * @param id          主键id
     * @param copyOptions copy条件
     * @return Vo对象
     */
    V getVoById(
            Serializable id,
            CopyOptions copyOptions
    );

    /**
     * 无copy条件根据id获取Vo对象.
     *
     * @param id 主键
     * @return Vo对象
     */
    default V getVoById(final Serializable id) {
        return getVoById(id, new CopyOptions());
    }

    /**
     * 自定义转换器根据id获取VO对象.
     *
     * @param id        主键id
     * @param convertor 自定义转换器
     * @return Vo对象
     */
    default V getVoById(
            final Serializable id,
            final Function<T, V> convertor
    ) {
        return convertor.apply(getById(id));
    }

    /**
     * 根据id集合获取Vo集合.
     *
     * @param idList      主键id集合
     * @param copyOptions copy条件
     * @return V对象
     */
    List<V> listVoByIds(
            Collection<? extends Serializable> idList,
            CopyOptions copyOptions
    );

    /**
     * 无copy条件根据id集合获取Vo集合.
     *
     * @param idList id集合
     * @return Vo集合
     */
    default List<V> listVoByIds(final Collection<? extends Serializable> idList) {
        return listVoByIds(idList, new CopyOptions());
    }

    /**
     * 根据id集合获取vo集合.
     *
     * @param idList    主键id集合
     * @param convertor 自定义转换器
     * @return Vo对象集合
     */
    default List<V> listVoByIds(
            final Collection<? extends Serializable> idList,
            final Function<Collection<T>, List<V>> convertor
    ) {
        final List<T> list = getBaseMapper().selectBatchIds(idList);
        if (list == null) {
            return Collections.emptyList();
        }
        return convertor.apply(list);
    }


    /**
     * 实体集合转VO集合.
     *
     * @param queryWrapper 查询条件
     * @param copyOptions  copy条件
     * @return Vo对象
     */
    List<V> listVo(
            Wrapper<T> queryWrapper,
            CopyOptions copyOptions
    );


    /**
     * 查询所有并转为VO.
     *
     * @return Vo集合
     */
    default List<V> listVo() {
        return listVo(Wrappers.emptyWrapper(), new CopyOptions());
    }


    /**
     * 条件查询集合并转为vo集合.
     *
     * @param queryWrapper 查询条件
     * @return vo集合
     */
    default List<V> listVo(final Wrapper<T> queryWrapper) {
        return listVo(queryWrapper, new CopyOptions());
    }

    /**
     * 自定义查询后自定义转换.
     *
     * @param queryWrapper 查询条件构造器
     * @param convertor    自定义转换器
     * @return 转换结果
     */
    default List<V> listVo(
            final Wrapper<T> queryWrapper,
            final Function<Collection<T>,
                    List<V>> convertor
    ) {
        final List<T> list = getBaseMapper().selectList(queryWrapper);
        if (list == null) {
            return Collections.emptyList();
        }
        return convertor.apply(list);
    }

    /**
     * 查询所有后自定义转换.
     *
     * @param convertor 自定义转换器
     * @return 转换结果
     */
    default List<V> listVo(final Function<Collection<T>, List<V>> convertor) {
        return listVo(Wrappers.emptyWrapper(), convertor);
    }

    /**
     * 根据DTO查询vo集合.
     *
     * @param dto 数据传输对象
     * @param <D> DTO class
     * @return VoList
     */
    <D> List<V> listVoByDTO(final D dto);


    /**
     * 根据map进行查询.
     *
     * @param columnMap   表字段 map 对象: 字段名,值
     * @param copyOptions copy条件
     * @return Vo对象
     */
    List<V> listVoByMap(
            Map<String, Object> columnMap,
            CopyOptions copyOptions
    );

    /**
     * 无copy条件根据map进行查询.
     *
     * @param columnMap 表字段 map 对象: 字段名,值
     * @return Vo对象
     */
    default List<V> listVoByMap(final Map<String, Object> columnMap) {
        return listVoByMap(columnMap, new CopyOptions());
    }

    /**
     * 自定义转换器根据map进行查询.
     *
     * @param columnMap 表字段 map 对象: 字段名,值
     * @param convertor 自定义转换器
     * @return Vo对象
     */
    default List<V> listVoByMap(
            final Map<String, Object> columnMap,
            final Function<Collection<T>, List<V>> convertor
    ) {
        final List<T> list = getBaseMapper().selectByMap(columnMap);
        if (list == null) {
            return Collections.emptyList();
        }
        return convertor.apply(list);
    }


    /**
     * 查询分页实体集合并转Vo集合.
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @param copyOptions  自定义转换器
     * @return Vo对象
     */
    PagePlus<T, V> pageVo(
            PagePlus<T, V> page,
            Wrapper<T> queryWrapper,
            CopyOptions copyOptions
    );

    /**
     * 自定义转换器,分页实体集合转Vo集合.
     *
     * @param page        分页对象
     * @param copyOptions 自定义转换器
     * @return Vo对象
     */
    default PagePlus<T, V> pageVo(
            final PagePlus<T, V> page,
            final CopyOptions copyOptions
    ) {
        return pageVo(page, Wrappers.emptyWrapper(), copyOptions);
    }

    /**
     * 分页实体集合转Vo集合.
     *
     * @param page 分页对象
     * @return 分页
     */
    default PagePlus<T, V> pageVo(final PagePlus<T, V> page) {
        return pageVo(page, Wrappers.emptyWrapper(), new CopyOptions());
    }

    /**
     * 分页实体集合转Vo集合.
     *
     * @param page    分页对象
     * @param wrapper 查询条件
     * @return 分页对象
     */
    default PagePlus<T, V> pageVo(
            final PagePlus<T, V> page,
            final Wrapper<T> wrapper
    ) {
        return pageVo(page, wrapper, new CopyOptions());
    }

    /**
     * 分页实体集合转Vo集合.
     *
     * @param page 分页对象
     * @param dto  查询条件
     * @param <D>  dto 数据传输对象
     * @return 分页对象
     */
    <D> PagePlus<T, V> pageVo(
            PagePlus<T, V> page,
            D dto
    );

    /**
     * 有查询条件,自定义转换分页集合.
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @param convertor    自定义转换器
     * @return 分页集合
     */
    default PagePlus<T, V> pageVo(
            final PagePlus<T, V> page,
            final Wrapper<T> queryWrapper,
            final Function<Collection<T>,
                    List<V>> convertor
    ) {
        final PagePlus<T, V> result = getBaseMapper().selectPage(page, queryWrapper);
        return result.setRecordsVo(convertor.apply(result.getRecords()));
    }

    /**
     * 无查询条件,自定义转换分页集合.
     *
     * @param page      分页对象
     * @param convertor 自定义转换器
     * @return 分页后集合
     */
    default PagePlus<T, V> pageVo(
            final PagePlus<T, V> page,
            final Function<Collection<T>,
                    List<V>> convertor
    ) {
        return pageVo(page, Wrappers.emptyWrapper(), convertor);
    }

    /**
     * 单sql批量插入.
     *
     * @param entityList 实体集合
     * @return 是否成功
     */
    boolean saveAll(Collection<T> entityList);


    /**
     * 保存前的数据校验.
     *
     * @param t 实体类
     */
    void validEntityBeforeSave(T t);


    /**
     * 通过DTO进行新增.
     *
     * @param dto 接收到数据
     * @param <D> 数据传输对象
     * @return 是否保存成功
     */
    default <D> Boolean insertByDTO(final D dto) {
        final T t = BeanUtil.toBean(dto, getEntityClass());
        validEntityBeforeSave(t);
        return save(t);
    }

    /**
     * 通过DTO进行修改.
     *
     * @param dto 数据传输对象
     * @param <D> DTO class
     * @return 是否修改成功
     */
    default <D> Boolean updateByDTO(final D dto) {
        final T t = BeanUtil.toBean(dto, getEntityClass());
        validEntityBeforeSave(t);
        return updateById(t);
    }

    /**
     * 删除前操作.
     *
     * @param id 需要删除的数据id
     */
    void validEntityBeforeDel(Long id);

    /**
     * 做操作后删除.
     *
     * @param id 需要删除的数据id
     * @return 是否成功
     */
    default Boolean deleteWithValidById(final Long id) {
        validEntityBeforeDel(id);
        return removeById(id);
    }

}
