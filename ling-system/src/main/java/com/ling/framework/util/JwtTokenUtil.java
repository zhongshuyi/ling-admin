package com.ling.framework.util;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.ling.common.util.RedisUtils;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.model.AdminUserDetails;
import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * Jwt 工具类.
 *
 * @author 钟舒艺
 * @version 1.0
 **/
@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    /**
     * 一秒.
     */
    protected static final long MILLIS_SECOND = 1000;
    /**
     * 一分钟.
     */
    protected static final long MILLIS_MINUTE = 60 * JwtTokenUtil.MILLIS_SECOND;
    private static final long serialVersionUID = 8404670744997523298L;
    /**
     * 刷新时间,距离过期时间小于20分钟.
     */
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    /**
     * 配置信息.
     */
    private final CustomConfig config;


    /**
     * 根据负载生成JWT的token,不设置过期时间,因为不存重要信息.
     *
     * @param claims 存的信息
     * @return token
     */
    private String generateToken(final Map<String, Object> claims) {
        return JWTUtil.createToken(claims, config.getToken().getSecret().getBytes());
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
        final Long expiration = config.getToken().getExpiration();
        user.setExpireTime(expiration * JwtTokenUtil.MILLIS_MINUTE);
        // 储存至redis
        JwtTokenUtil.log.info("过期时间:" + expiration);
        RedisUtils.setCacheObject(getTokenKey(uuid), user, Duration.ofMinutes(expiration));
        final Map<String, Object> claims = new HashMap<>(1);
        claims.put(config.getToken().getUserKey(), uuid);
        return generateToken(claims);
    }

    /**
     * 验证token是否有效.
     *
     * @param token token
     * @return 是否有效
     */
    private boolean validationToken(final String token) {
        return JWTUtil.verify(token, config.getToken().getSecret().getBytes());
    }


    /**
     * 根据请求获取token.
     *
     * @param request 请求
     * @return token
     */
    private String getToken(final HttpServletRequest request) {
        String token = request.getHeader(config.getToken().getTokenHeader());
        if (CharSequenceUtil.isNotEmpty(token) && token.startsWith(config.getToken().getTokenPrefix())) {
            token = token.replace(config.getToken().getTokenPrefix(), "");
        }
        return token;
    }

    /**
     * 根据请求获取到token,然后从redis中取出用户的各种信息.
     *
     * @param request 请求
     * @return 用户详细信息
     */
    public AdminUserDetails getAdminUserDetails(final HttpServletRequest request) {
        final String token = getToken(request);
        // 检查token是否被篡改
        if (CharSequenceUtil.isNotEmpty(token) && validationToken(token)) {
            // 解析token获取存的负载对象
            final JWT jwt = JWTUtil.parseToken(token);
            final String uuid = jwt.getPayload(config.getToken().getUserKey()).toString();
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
        if (expireTime - currentTime <= JwtTokenUtil.MILLIS_MINUTE_TEN) {
            user.setLoginTime(System.currentTimeMillis());
            user.setExpireTime(user.getLoginTime() + expireTime * JwtTokenUtil.MILLIS_MINUTE);
            RedisUtils.setCacheObject(
                    getTokenKey(user.getUuid()),
                    user,
                    Duration.ofMinutes(config.getToken().getExpiration())
            );
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
                    getTokenKey(user.getUuid()), user, Duration.ofMinutes(config.getToken().getExpiration()));
        }
    }

    /**
     * 拼接储存key.
     *
     * @param uuid uuid
     * @return 拼接后key
     */
    private String getTokenKey(final String uuid) {
        return config.getToken().getUserKey() + ":" + uuid;
    }
}
