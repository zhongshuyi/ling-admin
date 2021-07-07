package com.mall.common.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author 钟舒艺
 * @date 2021-06-24-16:00
 **/
@SuppressWarnings("unused")
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存基本的对象
     *
     * @param key   键
     * @param value 值
     * @param time  时间(分钟)
     * @param <T>   实体
     */
    public <T> void set(String key, T value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    /**
     * 保存属性
     *
     * @param key   键
     * @param value 值
     * @param <T>   实体
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取属性
     *
     * @param key 键
     * @param <T> 实体
     * @return 实体
     */
    @SuppressWarnings("unchecked")
    public  <T> T get(String key) {

        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除属性
     *
     * @param key 键
     * @return 是否成功
     */
    public  Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除属性
     *
     * @param keys 键集合
     * @return 成功个数
     */
    public  Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     *
     * @param key  键
     * @param time 时间
     * @return 是否成功
     */
    public  Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断是否有该属性
     *
     * @param key 键名
     * @return 是否有
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 按delta递增
     *
     * @param key   键
     * @param delta 递增因子
     * @return 递增后该键的值
     */
    public Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 按delta递减
     *
     * @param key   键
     * @param delta 递增因子
     * @return 递减后的值
     */
    public Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 获取Hash结构中的属性
     *
     * @param key     键
     * @param hashKey hash键
     * @return 属性值
     */
    @SuppressWarnings("unchecked")
    public  <T> T hGet(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param value   值
     * @param time    时间
     * @param <T>     实体
     * @return 是否成功
     */
    public  <T> Boolean hSet(String key, String hashKey, T value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     键
     * @param hashKey hash键
     * @param value   值
     */
    public <T> void hSet(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 直接获取整个Hash结构
     *
     * @param key 键
     * @return map
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 直接设置整个Hash结构
     *
     * @param key  键
     * @param map  键值对集合
     * @param time 时间
     * @return 是否成功
     */
    public  Boolean hSetAll(String key, Map<String, Object> map, long time){
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    /**
     * 直接设置整个Hash结构
     *
     * @param key 键
     * @param map 键值对集合
     */
    public void hSetAll(String key, Map<String, ?> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 删除Hash结构中的属性
     *
     * @param key     键
     * @param hashKey 哈希键
     */
    public void hDel(String key, Object... hashKey){
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return 是否成功
     */
    public Boolean hHasKey(String key, String hashKey){
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * Hash结构中属性递增
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param delta   递增因子
     * @return 递增后的值
     */
    public Long hIncr(String key, String hashKey, Long delta){
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * Hash结构中属性递减
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param delta   递增因子
     * @return 递减后的值
     */
    public  Long hDecr(String key, String hashKey, Long delta){
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }
    /**
     * 获取Set结构
     *
     * @param key 键
     * @return set集合
     */
    public Set<Object> sMembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 向Set结构中添加属性
     *
     * @param key    键
     * @param values 一个或多个值
     * @return 成功次数
     */
    public  Long sAdd(String key, Object... values){
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 向Set结构中添加属性
     *
     * @param key    键
     * @param time   时间
     * @param values 一个或多个值
     * @return 成功次数
     */
    public Long sAdd(String key, long time, Object... values){
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    /**
     * 是否为Set中的属性
     *
     * @param key   键
     * @param value 值
     * @return 是否是
     */
    public Boolean sIsMember(String key, Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取Set结构的长度
     *
     * @param key 键
     * @return set长度
     */
    public Long sSize(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 删除Set结构中的属性
     *
     * @param key    键
     * @param values 一个或多个值
     * @return 删除成功个数
     */
    public Long sRemove(String key, Object... values){
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 获取List结构中的属性
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return list集合
     */
    public List<Object> lRange(String key, long start, long end){
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取List结构的长度
     *
     * @param key 键
     * @return list长度
     */
    public Long lSize(String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引获取List中的属性
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    public Object lIndex(String key, long index){
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 向List结构中添加属性
     *
     * @param key   键
     * @param value 值
     * @return 成功次数
     */
    public Long lPush(String key, Object value){
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向List结构中添加属性
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return 成功次数
     */
    public Long lPush(String key, Object value, long time){
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    /**
     * 向List结构中批量添加属性
     *
     * @param key    键
     * @param values 一个或多个值
     * @return 成功个数
     */
    public  Long lPushAll(String key, Object... values){
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向List结构中批量添加属性
     *
     * @param key    键
     * @param time   时间
     * @param values 一个或多个值
     * @return 成功个数
     */
    public Long lPushAll(String key, Long time, Object... values){
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    /**
     * 从List结构中移除属性
     *
     * @param key   键
     * @param count count> 0：删除等于从左到右移动的值的第一个元素;count< 0：删除等于从右到左移动的值的第一个元素; count = 0：删除等于value的所有元素。
     * @param value 值
     * @return 删除的个数
     */
    public  Long lRemove(String key, long count, Object value){
        return redisTemplate.opsForList().remove(key, count, value);
    }
}
