package com.mall.common.core.mybatisplus.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.mall.common.util.RedisUtils;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

/**
 * mybatis-redis 二级缓存.
 * <p>使用方法 配置文件开启 mybatis-plus 二级缓存</p>
 * 在 XxxMapper.java 类上添加注解 @CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction
 * = MybatisPlusRedisCache.class).
 *
 * @author 钟舒艺
 **/
@Slf4j
public class MybatisPlusRedisCache implements Cache {

    /**
     * id.
     */
    private final String id;

    /**
     * 读写锁.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    /**
     * 构造方法.
     *
     * @param id 唯一键
     */
    public MybatisPlusRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }


    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public final void putObject(final Object o, final Object o1) {
        if (o1 != null) {
            RedisUtils.setCacheObject(o.toString(), o1);
        }
    }

    @Override
    public final Object getObject(final Object o) {
        try {
            if (o != null) {
                return RedisUtils.getCacheObject(o.toString());
            }
        } catch (final Exception e) {
            log.error("缓存出错", e);
        }
        return null;
    }

    @Override
    public final Object removeObject(final Object o) {
        if (o != null) {
            RedisUtils.deleteObject(o.toString());
        }
        return null;
    }

    @Override
    public final void clear() {
        log.debug("清空缓存");
        final Collection<String> keys = RedisUtils.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            RedisUtils.deleteObject(keys);
        }
    }

    @Override
    public final int getSize() {
        final RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("redisTemplate");
        final Long size = redisTemplate.execute(RedisServerCommands::dbSize);
        assert size != null;
        return size.intValue();
    }

    @Override
    public final ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
