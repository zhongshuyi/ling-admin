package com.ling.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.redisson.api.RBatch;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

/**
 * redis 工具类.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
public final class RedisUtils {

    /**
     * RedissonClient.
     */
    private static final RedissonClient CLIENT = SpringUtil.getBean(RedissonClient.class);


    private RedisUtils() {
    }


    /**
     * 发布通道消息.
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     * @param consumer   自定义处理
     * @param <T>        实体
     */
    public static <T> void publish(
            final String channelKey,
            final T msg,
            final Consumer<T> consumer
    ) {
        final RTopic topic = RedisUtils.CLIENT.getTopic(channelKey);
        topic.publish(msg);
        consumer.accept(msg);
    }

    /**
     * 发布通道消息.
     *
     * @param channelKey 通道key
     * @param msg        发送数据
     * @param <T>        实体
     */
    public static <T> void publish(
            final String channelKey,
            final T msg
    ) {
        final RTopic topic = RedisUtils.CLIENT.getTopic(channelKey);
        topic.publish(msg);
    }

    /**
     * 订阅通道接收消息.
     *
     * @param channelKey 通道key
     * @param clazz      消息类型
     * @param consumer   自定义处理
     * @param <T>        实体
     */
    public static <T> void subscribe(
            final String channelKey,
            final Class<T> clazz,
            final Consumer<T> consumer
    ) {
        final RTopic topic = RedisUtils.CLIENT.getTopic(channelKey);
        topic.addListener(clazz, (channel, msg) -> consumer.accept(msg));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等.
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @param <T>   实体
     */
    public static <T> void setCacheObject(
            final String key,
            final T value
    ) {
        RedisUtils.CLIENT.getBucket(key).set(value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等.
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param duration 时间
     * @param <T>      实体
     */
    public static <T> void setCacheObject(
            final String key,
            final T value,
            final Duration duration
    ) {
        final RBucket<T> result = RedisUtils.CLIENT.getBucket(key);
        result.set(value);
        result.expire(duration);
    }

    /**
     * 设置有效时间.
     *
     * @param key     Redis键
     * @param timeout 超时时间(单位:分钟)
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(
            final String key,
            final long timeout
    ) {
        return RedisUtils.expire(key, Duration.ofMinutes(timeout));
    }

    /**
     * 设置有效时间.
     *
     * @param key      Redis键
     * @param duration 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(
            final String key,
            final Duration duration
    ) {
        final RBucket<Object> rBucket = RedisUtils.CLIENT.getBucket(key);
        return rBucket.expire(duration);
    }

    /**
     * 获得缓存的基本对象.
     *
     * @param key 缓存键值
     * @param <T> 实体
     * @return 缓存键值对应的数据
     */
    public static <T> T getCacheObject(final String key) {
        final RBucket<T> rBucket = RedisUtils.CLIENT.getBucket(key);
        return rBucket.get();
    }

    /**
     * 删除单个对象.
     *
     * @param key 键
     * @return 是否删除成功
     */
    public static boolean deleteObject(final String key) {
        return RedisUtils.CLIENT.getBucket(key).delete();
    }


    /**
     * 删除集合对象.
     *
     * @param collection 多个对象
     * @param <T>        集合类型
     */
    public static <T> void deleteObject(final Collection<T> collection) {
        final RBatch batch = RedisUtils.CLIENT.createBatch();
        collection.forEach(t -> batch.getBucket(t.toString()).deleteAsync());
        batch.execute();
    }

    /**
     * 缓存List数据.
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @param <T>      集合类型
     * @return 缓存的对象
     */
    public static <T> boolean setCacheList(
            final String key,
            final List<T> dataList
    ) {
        final RList<T> list = RedisUtils.CLIENT.getList(key);
        return list.addAll(dataList);
    }

    /**
     * 获得缓存的list对象.
     *
     * @param key 缓存的键值
     * @param <T> 集合类型
     * @return 缓存键值对应的数据
     */
    public static <T> List<T> getCacheList(final String key) {
        final RList<T> list = RedisUtils.CLIENT.getList(key);
        return list.readAll();
    }

    /**
     * 缓存Set.
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @param <T>     类型
     * @return 缓存数据的对象
     */
    public static <T> boolean setCacheSet(
            final String key,
            final Set<T> dataSet
    ) {
        final RSet<T> set = RedisUtils.CLIENT.getSet(key);
        return set.addAll(dataSet);
    }

    /**
     * 获得缓存的set.
     *
     * @param key 键
     * @param <T> 类型
     * @return 值-Set集合
     */
    public static <T> Set<T> getCacheSet(final String key) {
        final RSet<T> set = RedisUtils.CLIENT.getSet(key);
        return set.readAll();
    }

    /**
     * 缓存Map.
     *
     * @param key     键
     * @param <T>     类型
     * @param dataMap map对象
     */
    public static <T> void setCacheMap(
            final String key,
            final Map<String, T> dataMap
    ) {
        if (dataMap != null) {
            final RMap<String, T> map = RedisUtils.CLIENT.getMap(key);
            map.putAll(dataMap);
        }
    }

    /**
     * 获得缓存的Map.
     *
     * @param key 键
     * @param <T> 类型
     * @return map对象
     */
    public static <T> Map<String, T> getCacheMap(final String key) {
        final RMap<String, T> map = RedisUtils.CLIENT.getMap(key);
        return map.getAll(map.keySet());
    }

    /**
     * 往Hash中存入数据.
     *
     * @param key     Redis键
     * @param hashKey Hash键
     * @param <T>     类型
     * @param value   值
     */
    public static <T> void setCacheMapValue(
            final String key,
            final String hashKey,
            final T value
    ) {
        final RMap<String, T> map = RedisUtils.CLIENT.getMap(key);
        map.put(hashKey, value);
    }

    /**
     * 获取Hash中的数据.
     *
     * @param key     Redis键
     * @param hashKey Hash键
     * @param <T>     类型
     * @return Hash中的对象
     */
    public static <T> T getCacheMapValue(
            final String key,
            final String hashKey
    ) {
        final RMap<String, T> map = RedisUtils.CLIENT.getMap(key);
        return map.get(hashKey);
    }

    /**
     * 获取多个Hash中的数据.
     *
     * @param key      Redis键
     * @param hashKeys Hash键集合
     * @param <K>      键类型
     * @param <V>      值类型
     * @return Hash对象集合
     */
    public static <K, V> Map<K, V> getMultiCacheMapValue(
            final String key,
            final Set<K> hashKeys
    ) {
        final RMap<K, V> map = RedisUtils.CLIENT.getMap(key);
        return map.getAll(hashKeys);
    }

    /**
     * 获得缓存的基本对象列表.
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public static Collection<String> keys(final String pattern) {
        final Iterable<String> iterable = RedisUtils.CLIENT.getKeys().getKeysByPattern(pattern);
        return CollUtil.newArrayList(iterable);
    }
}
