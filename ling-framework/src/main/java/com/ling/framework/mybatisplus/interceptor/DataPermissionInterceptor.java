package com.ling.framework.mybatisplus.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.ling.framework.mybatisplus.helper.DataPermissionHelper;
import java.sql.Connection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 数据权限修改版.
 *
 * @author 钟舒艺
 * @date 2022-05-18-17:16
 **/
@Slf4j
public class DataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {


    /**
     * 数据权限判断等操作.
     */
    final DataPermissionHelper dataPermissionHelper = new DataPermissionHelper();


    /**
     * 查询前置操作.
     *
     * @param executor      操作对象
     * @param ms            Mybatis 通过解析 XML 和 mapper 接口上的注解，生成 sql 对应的 MappedStatement 实例
     * @param parameter     请求参数
     * @param rowBounds     逻辑分页使用类
     * @param resultHandler 结果处理
     * @param boundSql      要执行的SQL
     */
    @Override
    public final void beforeQuery(
            final Executor executor,
            final MappedStatement ms,
            final Object parameter,
            final RowBounds rowBounds,
            final ResultHandler resultHandler,
            final BoundSql boundSql) {
        DataPermissionInterceptor.log.info("执行方法: " + ms.getId());
        // 解析 sql 分配对应方法
        final PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        log.info("修改后sql:" + mpBs.sql());
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }

    /**
     * 操作前置处理器,增删查改都会执行.
     *
     * @param sh                 处理器
     * @param connection         连接
     * @param transactionTimeout 时间
     */
    @Override
    public final void beforePrepare(
            final StatementHandler sh,
            final Connection connection,
            final Integer transactionTimeout) {
        final PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        final MappedStatement ms = mpSh.mappedStatement();
        final SqlCommandType sct = ms.getSqlCommandType();
        // 如果是更新和删除操作则添加条件
        if (sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
                return;
            }
            final PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(parserMulti(mpBs.sql(), ms.getId()));
        }
    }


    @Override
    protected final void processDelete(
            final Delete delete,
            final int index,
            final String sql,
            final Object obj) {
        final Expression newWhere = dataPermissionHelper.getNewWhere(delete.getWhere(), (String) obj);
        if (ObjectUtil.isNotEmpty(newWhere)) {
            delete.setWhere(newWhere);
        }
    }

    @Override
    protected final void processUpdate(
            final Update update,
            final int index,
            final String sql,
            final Object obj) {
        final Expression newWhere = dataPermissionHelper.getNewWhere(update.getWhere(), (String) obj);
        if (ObjectUtil.isNotEmpty(newWhere)) {
            update.setWhere(newWhere);
        }

    }

    @Override
    protected final void processSelect(
            final Select select,
            final int index,
            final String sql,
            final Object obj) {
        /*
             SelectBody有四个实现类,每个对应了常用的几种SELECT操作.
             PlainSelect:普通查询
             WithItem: WITH语句
             SetOperationList: INTERSECT、EXCEPT、MINUS、UNION语句等交并集查询
             ValuesStatement: VALUES语句
             使⽤JSqlParser进⾏解析SQL，如果是查询操作，则会根据语句，解析为以上四种查询对象中的⼀种
         */
        final SelectBody selectBody = select.getSelectBody();
        // 处理普通查询
        if (selectBody instanceof PlainSelect) {
            this.setWhere((PlainSelect) selectBody, (String) obj);
        } else if (selectBody instanceof SetOperationList) {
            // 处理交并集查询
            final SetOperationList setOperationList = (SetOperationList) selectBody;
            final List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
        }
    }

    /**
     * 设置查询的where条件.
     *
     * @param select 查询体
     * @param obj    执行方法的名称
     */
    private void setWhere(
            final PlainSelect select,
            final String obj) {
        final Expression newWhere = dataPermissionHelper.getNewWhere(select.getWhere(), obj);
        if (ObjectUtil.isNotEmpty(newWhere)) {
            select.setWhere(newWhere);
        }
    }


}
