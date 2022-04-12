package com.mall.framework.mybatisplus.interceptor;

import static com.mall.common.enums.DataScopeTypeEnum.ALL;
import static com.mall.common.enums.DataScopeTypeEnum.CUSTOM;
import static com.mall.common.enums.DataScopeTypeEnum.DEPT;
import static com.mall.common.enums.DataScopeTypeEnum.DEPT_AND_SUB;
import static com.mall.common.enums.DataScopeTypeEnum.ME;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.mall.common.annotation.DataScope;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.entity.UmsDept;
import com.mall.system.entity.UmsRole;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;


/**
 * 数据权限拦截器.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RequiredArgsConstructor
@Component
public class DataScopeInterceptor implements InnerInterceptor {

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public final void beforeQuery(
            final Executor executor,
            final MappedStatement ms,
            final Object parameter,
            final RowBounds rowBounds,
            final ResultHandler resultHandler,
            final BoundSql boundSql
    ) throws SQLException {
        //获取执行方法的位置
        final String namespace = ms.getId();
        //获取mapper名称
        final String className = namespace.substring(0, namespace.lastIndexOf("."));
        //获取方法名
        final String methodName = namespace.substring(namespace.lastIndexOf(".") + 1);
        //获取当前mapper 的方法
        final Method[] methods = ClassLoaderUtil.loadClass(className).getMethods();
        for (final Method m : methods) {
            if (methodName.equals(m.getName())) {
                log.info("找到方法:" + m.getName());
                //获取注解  来判断是不是要处理sql
                final DataScope dataScope = m.getAnnotation(DataScope.class);
                if (dataScope == null) {
                    continue;
                }
                //去除特殊字符
                final String originalSql = boundSql.getSql().replaceAll("[\r\n`]", "");
                log.warn("原始sql" + originalSql);
                try {
                    final Select select = (Select) CCJSqlParserUtil.parse(originalSql);
                    final PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
                    // 获取原始sql的条件
                    final Expression expression = plainSelect.getWhere();
                    // 获取新的and条件
                    final String condition = logicCondition();
                    final Expression envCondition = CCJSqlParserUtil.parseCondExpression(condition);
                    if (Objects.isNull(expression)) {
                        plainSelect.setWhere(envCondition);
                    } else {
                        // 判断新的and条件是不是空,如果是空的就是全部数据权限
                        if (CharSequenceUtil.isNotEmpty(condition)) {
                            final AndExpression andExpression = new AndExpression(expression, envCondition);
                            plainSelect.setWhere(andExpression);
                        }
                    }
                    // 通过反射修改sql语句
                    final Field field = boundSql.getClass().getDeclaredField("sql");
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, boundSql, plainSelect.toString());
                } catch (final JSQLParserException | NoSuchFieldException e) {
                    throw new BusinessErrorException(e);
                }

            }
        }
        InnerInterceptor.super.beforeQuery(
                executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    /**
     * 获取需要增加的条件(逻辑判断).
     *
     * @return sql
     */
    private String logicCondition() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        // 用户id
        final Long userId = adminUserDetails.getUmsAdmin().getId();
        // 用户角色
        final List<UmsRole> umsRoles = adminUserDetails.getRoles();
        // 用户部门
        final List<UmsDept> umsDepts = adminUserDetails.getDepts();
        // 部门id列表
        final List<Long> deptIds = umsDepts.stream().map(UmsDept::getId).collect(Collectors.toList());
        // 角色与部门都为空则抛出异常
        if (CollUtil.isEmpty(umsRoles) && CollUtil.isEmpty(umsDepts)) {
            throw new BusinessErrorException("未给用户分配角色或部门");
        } else if (CollUtil.isEmpty(umsRoles) && CollUtil.isNotEmpty(deptIds)) {
            // 如果角色为空则只查询本部门权限
            return getAddConditions(DEPT.getCode(), umsRoles, userId, deptIds);
        } else if (CollUtil.isEmpty(deptIds) && CollUtil.isNotEmpty(umsRoles)) {
            // 如果部门为空但有角色则只给查询本人数据
            return getAddConditions(ME.getCode(), umsRoles, userId, deptIds);
        } else if (umsRoles.size() == 1) {
            // 如果只有一个角色
            // 获取角色的数据权限
            final Integer dataScope = umsRoles.get(0).getDataScope();
            // 如果没有给角色设置数据权限,就默认只有本部门数据权限
            if (dataScope == null) {
                return getAddConditions(DEPT.getCode(), umsRoles, userId, deptIds);
            } else {
                return getAddConditions(dataScope, umsRoles, userId, deptIds);
            }
        } else {
            // 如果有多个角色,则获取所有角色的数据权限
            final Set<Integer> dataScopeCodes =
                    umsRoles.stream().map(UmsRole::getDataScope).collect(Collectors.toSet());
            // 如果拥有全部数据权限的角色
            if (dataScopeCodes.contains(ALL.getCode())) {
                return "";
            } else if (dataScopeCodes.contains(CUSTOM.getCode())) {
                // 如果角色的数据权限中有自定义数据权限
                // 只有两项
                final int dataScopeConcatTow = 2;
                if (dataScopeCodes.size() == dataScopeConcatTow) {
                    // 本部门权限与自定义权限
                    if (dataScopeCodes.contains(DEPT.getCode())) {
                        return getAddConditions(CUSTOM.getCode(), umsRoles, userId, deptIds)
                                + " or "
                                + getAddConditions(DEPT.getCode(), umsRoles, userId, deptIds);
                    }
                    // 自定数据权限+ 本部门及下级部门数据权限
                    if (dataScopeCodes.contains(DEPT_AND_SUB.getCode())) {
                        return getAddConditions(CUSTOM.getCode(), umsRoles, userId, deptIds)
                                + " or "
                                + getAddConditions(DEPT_AND_SUB.getCode(), umsRoles, userId, deptIds);
                    }
                    // 自定义数据权限加本人数据权限(使用自定义数据权限)
                    if (dataScopeCodes.contains(ME.getCode())) {
                        return getAddConditions(CUSTOM.getCode(), umsRoles, userId, deptIds);
                    }
                } else {
                    return getAddConditions(CUSTOM.getCode(), umsRoles, userId, deptIds)
                            + " or "
                            + getAddConditions(DEPT_AND_SUB.getCode(), umsRoles, userId, deptIds);
                }
                // 如果没有自定义数据权限且有本部门权限
            } else if (dataScopeCodes.contains(DEPT.getCode())) {
                // 有本部门及下级部门权限
                if (dataScopeCodes.contains(DEPT_AND_SUB.getCode())) {
                    return getAddConditions(DEPT_AND_SUB.getCode(), umsRoles, userId, deptIds);
                } else {
                    // 否则直接返回本部门权限
                    return getAddConditions(DEPT.getCode(), umsRoles, userId, deptIds);
                }
            }
            return "";
        }
    }


    /**
     * 获取条件字符串.
     *
     * @param dataScope 权限
     * @param roles     角色id列表
     * @param userId    用户id
     * @param deptIds   部门id列表
     * @return 要拼接的
     */
    private String getAddConditions(
            final Integer dataScope,
            final List<UmsRole> roles,
            final Long userId,
            final List<Long> deptIds
    ) {

        switch (dataScope) {
            // 自定数据权限
            case 2:
                final List<Long> roleIds =
                        roles.stream().map(UmsRole::getId).collect(Collectors.toList());
                return
                        CharSequenceUtil.format(
                                "dept_id in (SELECT dept_id FROM ums_role_dept where "
                                        + "role_id in({})) or user_id = {}",
                                CollUtil.join(roleIds, ","),
                                userId
                        );
            // 本部门数据权限
            case 3:
                return CharSequenceUtil.format("dept_id in ({})", CollUtil.join(deptIds, ","));
            // 本部门及下级部门数据权限
            case 4:
                if (deptIds.size() == 1) {
                    return CharSequenceUtil.format("dept_id in ("
                            + "select id ums_dept where parent_list like CONCAT("
                            + "select parent_list from ums_dept where id = {})", deptIds.get(0));
                } else {
                    return CharSequenceUtil.format("dept_id in (SELECT id "
                            + "from ums_dept "
                            + "where parent_list regexp"
                            + "      concat((SELECT group_concat(parent_list separator '|')"
                            + "              from ums_dept"
                            + "              where id in ({})));)", CollUtil.join(deptIds, ","));
                }
                // 仅本人数据权限
            default:
                return CharSequenceUtil.format("user_id = {}", userId);
        }
    }
}
