package com.ling.common.core.mybatisplus.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ling.common.core.interfaces.BaseConvert;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.mapstruct.factory.Mappers;

/**
 * 自定义 Service 接口, 实现 数据库实体与 vo 对象转换返回.
 *
 * @param <E> 实体类
 * @param <V> VO视图类
 * @param <D> DTO 数据传输类
 * @author 钟舒艺
 */
@SuppressWarnings("unused")
public interface IServicePlus<E, V, D> extends IService<E>, Serializable {


    /**
     * 根据id获取Vo对象.
     *
     * @param id          主键id
     * @param copyOptions copy条件
     * @return VO对象
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
            final Function<E, V> convertor
    ) {
        return convertor.apply(getById(id));
    }


    /**
     * 通过 mapstruct 转换,根据 id集合 获取 VO 对象.
     *
     * @param idList       主键id集合
     * @param convertClass mapstruct转换器
     * @return V对象
     */
    default List<V> listVoByIds(
            Collection<? extends Serializable> idList,
            Class<? extends BaseConvert<V, D, E>> convertClass) {
        return Mappers.getMapper(convertClass).convertToVOList(listByIds(idList));
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
            final Function<Collection<E>, List<V>> convertor
    ) {
        final List<E> list = getBaseMapper().selectBatchIds(idList);
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
            Wrapper<E> queryWrapper,
            CopyOptions copyOptions
    );

    /**
     * 通过 mapstruct 转换,根据 查询条件 获取 VO 对象.
     *
     * @param queryWrapper 查询条件
     * @param convertClass mapstruct转换器
     * @return V对象
     */
    default List<V> listVo(
            Wrapper<E> queryWrapper,
            Class<? extends BaseConvert<V, D, E>> convertClass) {
        return Mappers.getMapper(convertClass).convertToVOList(list(queryWrapper));
    }


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
    default List<V> listVo(final Wrapper<E> queryWrapper) {
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
            final Wrapper<E> queryWrapper,
            final Function<Collection<E>,
                    List<V>> convertor
    ) {
        final List<E> list = getBaseMapper().selectList(queryWrapper);
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
    default List<V> listVo(final Function<Collection<E>, List<V>> convertor) {
        return listVo(Wrappers.emptyWrapper(), convertor);
    }

    /**
     * 根据DTO查询vo集合.
     *
     * @param dto 数据传输对象
     * @return VoList
     */
    List<V> listVoByDTO(final D dto);


    /**
     * 通过 mapstruct 转换 ,根据DTO查询vo集合.
     *
     * @param dto          数据传输对象
     * @param convertClass 转换器
     * @return VO 集合
     */
    default List<V> listVoByDTO(
            final D dto,
            Class<? extends BaseConvert<V, D, E>> convertClass) {
        BaseConvert<V, D, E> convert = Mappers.getMapper(convertClass);
        return convert.convertToVOList(list(new QueryWrapper<>(convert.dtoToEntity(dto))));

    }


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
            final Function<Collection<E>, List<V>> convertor
    ) {
        final List<E> list = getBaseMapper().selectByMap(columnMap);
        if (list == null) {
            return Collections.emptyList();
        }
        return convertor.apply(list);
    }


    /**
     * 通过 mapstruct 转换,查询分页实体集合并转Vo集合.
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @param convertClass 自定义转换器
     * @return Vo对象
     */
    default PagePlus<E, V> pageVo(
            PagePlus<E, V> page,
            Wrapper<E> queryWrapper,
            Class<? extends BaseConvert<V, D, E>> convertClass
    ) {
        final PagePlus<E, V> result = getBaseMapper().selectPage(page, queryWrapper);
        return result.setRecordsVo(
                Mappers.getMapper(convertClass).convertToVOList(result.getRecords())
        );
    }


    /**
     * 查询分页实体集合并转Vo集合.
     *
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @param copyOptions  自定义转换器
     * @return Vo对象
     */
    PagePlus<E, V> pageVo(
            PagePlus<E, V> page,
            Wrapper<E> queryWrapper,
            CopyOptions copyOptions
    );

    /**
     * 自定义转换器,分页实体集合转Vo集合.
     *
     * @param page        分页对象
     * @param copyOptions 自定义转换器
     * @return Vo对象
     */
    default PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
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
    default PagePlus<E, V> pageVo(final PagePlus<E, V> page) {
        return pageVo(page, Wrappers.emptyWrapper(), new CopyOptions());
    }

    /**
     * 分页实体集合转Vo集合.
     *
     * @param page    分页对象
     * @param wrapper 查询条件
     * @return 分页对象
     */
    default PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
            final Wrapper<E> wrapper
    ) {
        return pageVo(page, wrapper, new CopyOptions());
    }

    /**
     * 分页实体集合转Vo集合.
     *
     * @param page 分页对象
     * @param dto  查询条件
     * @return 分页对象
     */
    PagePlus<E, V> pageVo(
            PagePlus<E, V> page,
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
    default PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
            final Wrapper<E> queryWrapper,
            final Function<Collection<E>,
                    List<V>> convertor
    ) {
        final PagePlus<E, V> result = getBaseMapper().selectPage(page, queryWrapper);
        return result.setRecordsVo(convertor.apply(result.getRecords()));
    }

    /**
     * 无查询条件,自定义转换分页集合.
     *
     * @param page      分页对象
     * @param convertor 自定义转换器
     * @return 分页后集合
     */
    default PagePlus<E, V> pageVo(
            final PagePlus<E, V> page,
            final Function<Collection<E>,
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
    boolean saveAll(Collection<E> entityList);


    /**
     * 保存前的数据校验.
     *
     * @param e 实体类
     */
    void validEntityBeforeSave(E e);


    /**
     * 通过 mapstruct 转换, 通过DTO进行新增.
     *
     * @param dto          接收到数据
     * @param convertClass mapstruct转换器
     * @return 是否保存成功
     */
    default Boolean saveByDTO(
            final D dto,
            Class<? extends BaseConvert<V, D, E>> convertClass) {
        final E e = Mappers.getMapper(convertClass).dtoToEntity(dto);
        validEntityBeforeSave(e);
        return save(e);
    }


    /**
     * 通过DTO进行新增.
     *
     * @param dto 接收到数据
     * @return 是否保存成功
     */
    default Boolean saveByDTO(final D dto) {
        final E e = BeanUtil.toBean(dto, getEntityClass());
        validEntityBeforeSave(e);
        return save(e);
    }

    /**
     * 通过DTO进行修改.
     *
     * @param dto 数据传输对象
     * @return 是否修改成功
     */
    default Boolean updateByDTO(final D dto) {
        final E e = BeanUtil.toBean(dto, getEntityClass());
        validEntityBeforeSave(e);
        return updateById(e);
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
