package com.mall.framework.util;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.mall.common.util.RedisUtils;
import com.mall.framework.model.AdminUserDetails;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Jwt 工具类.
 *
 * @author 钟舒艺
 * @version 1.0
 **/
@SuppressWarnings("unused")
@Component
@Slf4j
@ConfigurationProperties(prefix = "token")
@Data
public class JwtTokenUtil implements Serializable {

    /**
     * 一秒.
     */
    protected static final long MILLIS_SECOND = 1000;
    /**
     * 一分钟.
     */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private static final long serialVersionUID = 8404670744997523298L;
    /**
     * 刷新时间,距离过期时间小于20分钟.
     */
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;
    /**
     * 令牌秘钥,定义在application.yml中通过@ConfigurationProperties注解映射.
     */
    private String secret;
    /**
     * 令牌有效期,定义在application.yml中通过@ConfigurationProperties注解映射.
     */
    private Long expiration;
    /**
     * JWT存储的请求头.
     */
    private String tokenHeader;
    /**
     * 令牌前缀.
     */
    private String tokenPrefix;
    /**
     * token中储存的uuid键名.
     */
    private String userKey;

    /**
     * 根据负载生成JWT的token,不设置过期时间,因为不存重要信息.
     *
     * @param claims 存的信息
     * @return token
     */
    private String generateToken(final Map<String, Object> claims) {
        return JWTUtil.createToken(claims, secret.getBytes());
    }

    /**
     * 根据用户信息生成token.
     *
     * @param user 用户信息
     * @return token
     */
    public String generateToken(final AdminUserDetails user) {
        // 生成存储到redis中的键名
        final String uuid = UUID.fastUUID().toString();
        user.setUuid(uuid);
        user.setLoginTime(System.currentTimeMillis());
        user.setExpireTime(expiration * MILLIS_MINUTE);
        // 储存至redis
        log.info("过期时间:" + expiration);
        RedisUtils.setCacheObject(
                getTokenKey(uuid), user, expiration, TimeUnit.MINUTES);
        final Map<String, Object> claims = new HashMap<>(1);
        claims.put(userKey, uuid);
        return generateToken(claims);
    }

    /**
     * 验证token是否有效.
     *
     * @param token token
     * @return 是否有效
     */
    private boolean validationToken(final String token) {
        return JWTUtil.verify(token, secret.getBytes());
    }


    /**
     * 根据请求获取token.
     *
     * @param request 请求
     * @return token
     */
    private String getToken(final HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (CharSequenceUtil.isNotEmpty(token) && token.startsWith(tokenPrefix)) {
            token = token.replace(tokenPrefix, "");
        }
        return token;
    }

    /**
     * 根据请求获取到token,然后从redis中取出用户的各种信息.
     *
     * @param request 请求
     * @return 用户详细信息
     */
    public AdminUserDetails getAdminUserDetails(
            final HttpServletRequest request) {
        final String token = getToken(request);
        // 检查token是否被篡改
        if (CharSequenceUtil.isNotEmpty(token) && validationToken(token)) {
            // 解析token获取存的负载对象
            final JWT jwt = JWTUtil.parseToken(token);
            final String uuid = jwt.getPayload(userKey).toString();
            return RedisUtils.getCacheObject(getTokenKey(uuid));
        }
        return null;
    }


    /**
     * 刷新有效期.
     *
     * @param user 用户信息 包含uuid
     */
    public void refreshToken(final AdminUserDetails user) {
        final long expireTime = user.getExpireTime();
        final long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            user.setLoginTime(System.currentTimeMillis());
            user.setExpireTime(
                    user.getLoginTime() + expireTime * MILLIS_MINUTE);
            RedisUtils.setCacheObject(
                    getTokenKey(user.getUuid()),
                    user, expiration, TimeUnit.MINUTES);
        }
    }

    /**
     * 删除缓存的用户信息.
     *
     * @param uuid uuid
     * @return 是否删除成功
     */
    public boolean delUser(final String uuid) {
        if (CharSequenceUtil.isNotEmpty(uuid)) {
            return RedisUtils.deleteObject(getTokenKey(uuid));
        }
        return false;
    }

    /**
     * 更新用户的缓存.
     *
     * @param user 用户信息
     */
    public void setUser(final AdminUserDetails user) {
        if (user != null && CharSequenceUtil.isNotEmpty(user.getUuid())) {
            RedisUtils.setCacheObject(
                    getTokenKey(user.getUuid()),
                    user, expiration, TimeUnit.MINUTES);
        }
    }

    /**
     * 拼接储存key.
     *
     * @param uuid uuid
     * @return 拼接后key
     */
    private String getTokenKey(final String uuid) {
        return userKey + ":" + uuid;
    }
}
