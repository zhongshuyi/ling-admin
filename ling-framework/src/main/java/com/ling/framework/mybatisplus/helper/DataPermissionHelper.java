package com.ling.framework.mybatisplus.helper;

import static com.ling.common.enums.DataScopeTypeEnum.ALL;
import static com.ling.common.enums.DataScopeTypeEnum.CUSTOM;
import static com.ling.common.enums.DataScopeTypeEnum.DEPT_AND_SUB;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ling.common.annotation.DataPermission;
import com.ling.common.core.domain.model.LoginUser;
import com.ling.common.core.domain.model.SysDept;
import com.ling.common.core.domain.model.SysRole;
import com.ling.common.enums.DataScopeTypeEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.utils.SecurityUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

/**
 * 数据权限的帮手类.
 *
 * @author 钟舒艺
 * @date 2022-05-21-20:43
 **/
@Slf4j
public class DataPermissionHelper {

    /**
     * 方法或类(名称) 与 注解的映射关系缓存.
     */
    private final Map<String, DataPermission> dataPermissionCacheMap = new ConcurrentHashMap<>();

    /**
     * 缓存没有注解的方法.
     */
    private final Set<String> noAnnotationCache = new HashSet<>();

    /**
     * 获取用户的数据权限信息.
     *
     * @param loginUser 用户信息.
     * @return 数据权限列表
     */
    public static List<DataScopeTypeEnum> getDataPermissionTypeEnum(final LoginUser loginUser) {
        // 用户角色id列表
        final List<SysRole> sysSysRoles = loginUser.getSysRoles();
        // 部门id列表
        final List<Long> deptIds =
                loginUser.getSysDepts().stream().map(SysDept::getId).collect(Collectors.toList());
        // 获取所有角色的数据权限
        final Set<Integer> dataScopeCodes =
                sysSysRoles.stream().map(SysRole::getDataScope).collect(Collectors.toSet());
        // 角色与部门都为空则抛出异常
        if (CollUtil.isEmpty(sysSysRoles) && CollUtil.isEmpty(deptIds)) {
            throw new BusinessErrorException("未给用户分配角色或部门");
        }
        // 拥有全部数据权限
        if (dataScopeCodes.contains(ALL.getCode())) {
            return List.of(ALL);
        }
        // 如果角色为空则只查询本部门权限
        if (CollUtil.isEmpty(sysSysRoles) && CollUtil.isNotEmpty(deptIds)) {
            return List.of(DataScopeTypeEnum.DEPT);
        }
        // 如果部门为空但有角色则只给查询本人数据
        if (CollUtil.isNotEmpty(sysSysRoles) && CollUtil.isEmpty(deptIds)) {
            return List.of(DataScopeTypeEnum.ME);
        }
        // 角色与部门都不为空
        if (CollUtil.isNotEmpty(sysSysRoles) && CollUtil.isNotEmpty(deptIds)) {
            // 如果没有给角色赋数据权限则给本部门权限
            if (CollUtil.isEmpty(dataScopeCodes)) {
                return List.of(DataScopeTypeEnum.DEPT);
            }
            // 如果只有一种数据权限,那就用那个数据权限
            if (dataScopeCodes.size() == 1) {
                return List.of(DataScopeTypeEnum.class.getEnumConstants()[ListUtil.toList(dataScopeCodes).get(0) - 1]);
            }
            // 如果拥有自定义数据权限
            if (dataScopeCodes.contains(CUSTOM.getCode()) && dataScopeCodes.contains(DEPT_AND_SUB.getCode())) {
                return List.of(CUSTOM, DEPT_AND_SUB);
            }
            final List<DataScopeTypeEnum> dataScopeTypeEnumList = CollUtil.newArrayList();
            for (final Integer dataScopeCode : dataScopeCodes) {
                dataScopeTypeEnumList.add(DataScopeTypeEnum.class.getEnumConstants()[dataScopeCode - 1]);
            }
            return dataScopeTypeEnumList;
        }
        return CollUtil.newArrayList();
    }

    /**
     * 获取添加数据权限条件的where语句.
     *
     * @param where             原始条件
     * @param mappedStatementId 执行方法id
     * @return 新的where条件
     */
    public Expression getNewWhere(
            final Expression where,
            final String mappedStatementId) {
        // 获取注解
        final DataPermission dataPermission = findAnnotation(mappedStatementId);
        // 无注解则返回原始where
        if (ObjectUtil.isEmpty(dataPermission)) {
            log.info("本次查询无注解");
            return where;
        }
        final LoginUser loginUser = SecurityUtils.getLoginUser();
        // 如果是超级管理员则返回原始where
        if (Boolean.TRUE.equals(loginUser.getIsAdmin())) {
            return where;
        }
        assert dataPermission != null;
        final List<DataScopeTypeEnum> dataScopeTypeEnumList = new ArrayList<>();
        for (final Integer code : loginUser.getDataScopeTypeEnumCodeList()) {
            dataScopeTypeEnumList.add(DataScopeTypeEnum.class.getEnumConstants()[code - 1]);
        }
        // 构建新的where
        return buildNewWhere(where, dataScopeTypeEnumList, dataPermission);
    }

    /**
     * 构建新的where.
     *
     * @param where              原始where
     * @param dataScopeTypeEnums 数据权限
     * @param dataPermission     注解
     * @return 添加数据权限后的where
     */
    private Expression buildNewWhere(
            final Expression where,
            final List<DataScopeTypeEnum> dataScopeTypeEnums,
            final DataPermission dataPermission
    ) {
        final LoginUser loginUser = SecurityUtils.getLoginUser();
        // 用户id
        final Long userId = loginUser.getUser().getId();
        // 用户角色id列表
        final List<Long> sysRoleIds =
                loginUser.getSysRoles().stream().map(SysRole::getId).collect(Collectors.toList());
        // 部门id列表
        final List<Long> deptIds =
                loginUser.getSysDepts().stream().map(SysDept::getId).collect(Collectors.toList());
        try {
            StringBuilder addWhere = new StringBuilder();
            for (final DataScopeTypeEnum dataScopeTypeEnum : dataScopeTypeEnums) {
                addWhere.append(
                        dataScopeTypeEnum.buildTemplate(
                                userId,
                                sysRoleIds,
                                deptIds,
                                dataPermission.userIdColumnName(),
                                dataPermission.deptIdColumnName())
                ).append(" or ");
            }
            addWhere = new StringBuilder(addWhere.substring(0, addWhere.length() - 4));
            DataPermissionHelper.log.error(addWhere.toString());
            if (CharSequenceUtil.isEmpty(addWhere)) {
                return where;
            }
            if (ObjectUtil.isNotEmpty(where)) {
                return new AndExpression(where, CCJSqlParserUtil.parseCondExpression(addWhere.toString()));
            } else {
                return CCJSqlParserUtil.parseCondExpression(addWhere.toString());
            }
        } catch (final JSQLParserException e) {
            throw new BusinessErrorException("数据权限异常", e);
        }
    }

    /**
     * 获取方法或类的注解.
     *
     * @param mappedStatementId mapper完整方法名
     * @return 注解信息
     */
    private DataPermission findAnnotation(final String mappedStatementId) {
        // 如果之前这个方法没有注解则直接返回
        if (noAnnotationCache.contains(mappedStatementId)) {
            return null;
        }
        DataPermission dataPermission = dataPermissionCacheMap.get(mappedStatementId);
        final StringBuilder sb = new StringBuilder(mappedStatementId);
        final int index = sb.lastIndexOf(".");
        // 获取完整类名
        final String className = sb.substring(0, index);
        // 获取方法名
        final String methodName = sb.substring(index + 1, sb.length());
        // 获取当前mapper
        final Class<?> clazz = ClassUtil.loadClass(className);
        //获取当前mapper 的方法
        final Method[] methods = clazz.getMethods();
        for (final Method m : methods) {
            if (methodName.equals(m.getName())) {
                DataPermissionHelper.log.info("找到方法:" + m.getName());
                //获取注解来判断是不是要处理sql
                dataPermission = m.getAnnotation(DataPermission.class);
                if (ObjectUtil.isNotEmpty(dataPermission)) {
                    dataPermissionCacheMap.put(mappedStatementId, dataPermission);
                    // 方法上的注解优先级高,有注解则直接返回
                    return dataPermission;
                } else {
                    break;
                }
            }
        }
        // 如果方法上没有注解
        // 如果之前这个类没有注解则直接返回
        if (noAnnotationCache.contains(className)) {
            return dataPermission;
        }
        // 获取类注解
        dataPermission = clazz.getAnnotation(DataPermission.class);
        if (ObjectUtil.isEmpty(dataPermission)) {
            // 如果类和方法都没有就标记类与方法没有注解
            noAnnotationCache.add(mappedStatementId);
            noAnnotationCache.add(className);
        } else {
            dataPermissionCacheMap.put(className, dataPermission);
        }
        return dataPermission;
    }

}
