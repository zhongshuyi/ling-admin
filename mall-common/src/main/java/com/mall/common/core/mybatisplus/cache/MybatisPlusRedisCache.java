package com.mall.common.core.mybatisplus.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.mall.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * mybatis-redis 二级缓存
 * <p>
 * 使用方法 配置文件开启 mybatis-plus 二级缓存
 * 在 XxxMapper.java 类上添加注解 @CacheNamespace(implementation = MybatisPlusRedisCache.class, eviction = MybatisPlusRedisCache.class)
 *
 * @author 钟舒艺
 * @date 2021-11-09-21:11
 **/
@Slf4j
public class MybatisPlusRedisCache implements Cache {

    private final String id;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public MybatisPlusRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        if (o1 != null) {
            RedisUtils.setCacheObject(o.toString(), o1);
        }
    }

    @Override
    public Object getObject(Object o) {
        try {
            if (o != null) {
                return RedisUtils.getCacheObject(o.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("缓存出错");
        }
        return null;
    }

    @Override
    public Object removeObject(Object o) {
        if (o != null) {
            RedisUtils.deleteObject(o.toString());
        }
        return null;
    }

    @Override
    public void clear() {
        log.debug("清空缓存");
        Collection<String> keys = RedisUtils.keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            RedisUtils.deleteObject(keys);
        }
    }

    @Override
    public int getSize() {
        RedisTemplate<String, Object> redisTemplate = SpringUtil.getBean("redisTemplate");
        Long size = redisTemplate.execute(RedisServerCommands::dbSize);
        assert size != null;
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}