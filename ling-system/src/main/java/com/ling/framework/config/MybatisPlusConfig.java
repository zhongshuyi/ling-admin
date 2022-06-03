package com.ling.framework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ling.common.core.mybatisplus.methods.InsertAll;
import com.ling.framework.mybatisplus.handler.CreateAndUpdateMetaObjectHandler;
import com.ling.framework.mybatisplus.interceptor.DataPermissionInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatisPlus 配置.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Configuration
@MapperScan("${mybatis-plus.mapperPackage}")
@EnableTransactionManagement(proxyTargetClass = true)
@RequiredArgsConstructor
public class MybatisPlusConfig {

    /**
     * 数据权限拦截器.
     */
    private final DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();

    /**
     * 插件配置.
     *
     * @return 配置好拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusConfig.log.info("mybatisPlus 插件加载");
        final MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限
        interceptor.addInnerInterceptor(dataPermissionInterceptor);
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
        interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 分页插件，自动识别数据库类型.
     * <a href="https://baomidou.com/guide/interceptor-pagination.html">https://baomidou.com/guide/interceptor-pagination.html</a>
     *
     * @return 分页插件配置
     */
    private PaginationInnerInterceptor paginationInnerInterceptor() {
        final PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        // 分页合理化
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁插件.
     * <a href="https://baomidou.com/guide/interceptor-optimistic-locker.html">https://baomidou.com/guide/interceptor-optimistic-locker.html</a>
     *
     * @return 乐观锁插件配置
     */
    private OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 如果是对全表的删除或更新操作，就会终止该操作.
     * <a href="https://baomidou.com/guide/interceptor-block-attack.html">https://baomidou.com/guide/interceptor-block-attack.html</a>
     *
     * @return 全表操作拦截器
     */
    private BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }


    /**
     * 元对象字段填充控制器 用于填充创建时间/创建人/修改时间/修改人.
     * <a href="https://baomidou.com/guide/auto-fill-metainfo.html">https://baomidou.com/guide/auto-fill-metainfo.html</a>
     *
     * @return 元对象字段填充控制器
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CreateAndUpdateMetaObjectHandler();
    }

    /**
     * sql注入器配置.
     * <a href="https://baomidou.com/guide/sql-injector.html">https://baomidou.com/guide/sql-injector.html</a>
     *
     * @return sql注入器配置
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector() {
            @Override
            public List<AbstractMethod> getMethodList(
                    final Class<?> mapperClass,
                    final TableInfo tableInfo
            ) {
                final List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
                methodList.add(new InsertAll());
                return methodList;
            }
        };
    }
}
