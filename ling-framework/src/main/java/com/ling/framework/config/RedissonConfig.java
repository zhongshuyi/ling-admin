package com.ling.framework.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置.
 * 配置文档: <a href="https://github.com/redisson/redisson/wiki/2.-%E9%85%8D%E7%BD%AE%E6%96%B9%E6%B3%95">...</a>.
 *
 * @author 钟舒艺
 * @version 1.0
 **/
@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedissonConfig {

    /**
     * 创建 Redisson Bean.
     *
     * @return Bean
     * @throws IOException IO异常
     */
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() throws IOException {
        final Config config = Config.fromYAML(
                Thread.currentThread().getContextClassLoader().getResource("redisson-config.yml"));
        final RedissonClient redissonClient = Redisson.create(config);
        RedissonConfig.log.info("初始化 redisson 配置");
        return redissonClient;
    }

    /**
     * 整合spring-cache.
     *
     * @param redissonClient redisson
     * @return 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(final RedissonClient redissonClient) {
        final Map<String, CacheConfig> config = new HashMap<>(16);
        return new RedissonSpringCacheManager(redissonClient, config, JsonJacksonCodec.INSTANCE);
    }
}
