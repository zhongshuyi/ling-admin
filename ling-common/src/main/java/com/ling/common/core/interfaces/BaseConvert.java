package com.ling.common.core.interfaces;


import java.util.List;

/**
 * .mapstruct 转换的公共接口.
 *
 * @param <V> 视图层 VO 类
 * @param <D> 数据传输层 DTO 类
 * @param <E> 实体层 Entity 类
 * @author 钟舒艺
 * @since 2022-10-22 13:56
 */
public interface BaseConvert<V, D, E> {

    /**
     * 转化为VO.
     *
     * @param entity 实体
     * @return VO
     */
    V convertToVO(E entity);

    /**
     * 转换为VO集合.
     *
     * @param list 实体集合
     * @return VO集合
     */
    List<V> convertToVOList(List<E> list);

    /**
     * 转化为DTO.
     *
     * @param entity 实体
     * @return DTO
     */
    D convertToDTO(E entity);

    /**
     * 转换为DTO集合.
     *
     * @param list 实体集合
     * @return DTO集合
     */
    List<D> convertToDTOList(List<E> list);

    /**
     * VO转换为实体.
     *
     * @param vo vo
     * @return 实体
     */
    E voToEntity(V vo);


    /**
     * DTO转换为实体.
     *
     * @param dto 数据传输对象
     * @return 实体对象
     */
    E dtoToEntity(D dto);

    /**
     * VO 集合转化为 实体对象集合.
     *
     * @param voList 视图对象集合
     * @return 实体对象集合.
     */
    List<E> voListToEntityList(List<V> voList);

    /**
     * DTO 集合转化为 实体对象集合.
     *
     * @param dto 视图对象集合
     * @return 实体对象集合.
     */
    List<E> dtoListToEntityList(List<V> dto);
}
