package com.mall.common.core.mybatisplus.methods;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import java.util.List;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 批量插入增强
 *
 * @author 钟舒艺
 * @date 2021-10-28-21:29
 **/
public class InsertAll extends AbstractMethod {

    private static final long serialVersionUID = 5839519053059873280L;

    private static final String[] FILL_PROPERTY =
            {"createTime", "createBy", "updateTime", "updateBy"};

    @Override
    public MappedStatement injectMappedStatement(
            Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        final String sql = "<script>insert into %s %s values %s</script>";
        final String fieldSql = prepareFieldSql(tableInfo);
        final String valueSql = prepareValuesSqlForMysqlBatch(tableInfo);
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String sqlMethod = "insertAll";
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StrUtil.isNotBlank(tableInfo.getKeyProperty())) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                // 自增主键
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else {
                if (null != tableInfo.getKeySequence()) {
                    keyGenerator = TableInfoHelper
                            .genKeyGenerator(sqlMethod, tableInfo, builderAssistant);
                    keyProperty = tableInfo.getKeyProperty();
                    keyColumn = tableInfo.getKeyColumn();
                }
            }
        }
        final String sqlResult = String.format(sql, tableInfo.getTableName(), fieldSql, valueSql);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sqlResult, modelClass);
        return this.addInsertMappedStatement(
                mapperClass, modelClass, sqlMethod,
                sqlSource, keyGenerator, keyProperty, keyColumn);
    }


    /**
     * 列名集合
     *
     * @param tableInfo 表信息
     * @return 列名
     */
    private String prepareFieldSql(TableInfo tableInfo) {
        StringBuilder fieldSql = new StringBuilder();
        if (StrUtil.isNotBlank(tableInfo.getKeyColumn())) {
            fieldSql.append(tableInfo.getKeyColumn()).append(",");
        }
        tableInfo.getFieldList().forEach(x -> fieldSql.append(x.getColumn()).append(","));
        fieldSql.delete(fieldSql.length() - 1, fieldSql.length());
        fieldSql.insert(0, "(");
        fieldSql.append(")");
        return fieldSql.toString();
    }

    /**
     * 值的集合
     *
     * @param tableInfo 类信息
     * @return 值集合
     */
    private String prepareValuesSqlForMysqlBatch(TableInfo tableInfo) {
        final StringBuilder valueSql = new StringBuilder();
        valueSql.append("<foreach collection=\"list\" item=\"item\" index=\"index\""
                + " open=\"(\" separator=\"),(\" close=\")\">");
        if (StrUtil.isNotBlank(tableInfo.getKeyColumn())) {
            valueSql.append("\n#{item.").append(tableInfo.getKeyProperty()).append("},\n");
        }
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        int last = fieldList.size() - 1;
        for (int i = 0; i < fieldList.size(); i++) {
            String property = fieldList.get(i).getProperty();
            if (!StrUtil.equalsAny(property, FILL_PROPERTY)) {
                valueSql.append("<if test=\"item.").append(property).append(" != null\">");
                valueSql.append("#{item.").append(property).append("}");
                if (i != last) {
                    valueSql.append(",");
                }
                valueSql.append("</if>");
                valueSql.append("<if test=\"item.").append(property).append(" == null\">");
                valueSql.append("DEFAULT");
                if (i != last) {
                    valueSql.append(",");
                }
                valueSql.append("</if>");
            } else {
                valueSql.append("#{item.").append(property).append("}");
                if (i != last) {
                    valueSql.append(",");
                }
            }
        }
        valueSql.append("</foreach>");
        return valueSql.toString();
    }
}
