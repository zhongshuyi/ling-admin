package com.mall.framework.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.mall.common.core.redis.RedisService;
import com.mall.framework.model.AdminUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 钟舒艺
 * @date 2021-06-29-21:06
 **/
@Component
@SuppressWarnings("unused")
@ConfigurationProperties("token")
@Slf4j
public class JwtTokenUtil {

    /**
     * redis操作
     */

    private final RedisService redisService;

    /**
     * 令牌秘钥,定义在application.yml中通过@ConfigurationProperties注解映射
     */
    private String secret;

    /**
     * 令牌有效期,定义在application.yml中通过@ConfigurationProperties注解映射
     */
    private Long expiration;

    /**
     * JWT存储的请求头
     */
    private String tokenHeader;

    /**
     * 令牌前缀
     */
    private String tokenPrefix;

    /**
     * token中储存的uuid键名
     */
    private String userKey;

    /**
     * 一秒
     */
    protected static final long MILLIS_SECOND = 1000;

    /**
     * 一分钟
     */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    /**
     * 刷新时间,距离过期时间小于20分钟
     */
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;


    public JwtTokenUtil(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 根据负载生成JWT的token,不设置过期时间,因为不存重要信息
     *
     * @param claims 存的信息
     * @return token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                // jwt内存的信息
                .setClaims(claims)
                // 加密方式
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 根据请求获取token
     *
     * @param request 请求
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        if (StrUtil.isNotEmpty(token) && token.startsWith(tokenPrefix)) {
            token = token.replace(tokenPrefix, "");
        }
        return token;
    }

    /**
     * 根据请求获取到token,然后从redis中取出用户的各种信息
     *
     * @param request 请求
     * @return 用户详细信息
     */
    public AdminUserDetails getAdminUserDetails(HttpServletRequest request) {
        String token = getToken(request);
        if (StrUtil.isNotEmpty(token)) {
            // 解析token获取存的负载对象
            Claims claims = getClaimsFromToken(token);
            String uuid = (String) claims.get(userKey);
            return redisService.get(getTokenKey(uuid));
        }
        return null;
    }


    /**
     * 根据用户信息生成token
     */
    public String generateToken(AdminUserDetails user) {
        // 生成存储到redis中的键名
        String uuid = UUID.fastUUID().toString();
        user.setUuid(uuid);
        user.setLoginTime(System.currentTimeMillis());
        user.setExpireTime(expiration * MILLIS_MINUTE);
        // 储存至redis
        redisService.set(getTokenKey(uuid), user, expiration);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(userKey, user);
        return generateToken(claims);
    }

    /**
     * 刷新有效期
     *
     * @param user 用户信息 包含uuid
     */
    public void refreshToken(AdminUserDetails user) {
        long expireTime = user.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            redisService.set(getTokenKey(user.getUuid()), user, expiration);
        }
    }

    /**
     * 删除缓存的用户信息
     * @param uuid uuid
     */
    public void delUser(String uuid){
        if(StrUtil.isNotEmpty(uuid)){
            redisService.del(getTokenKey(uuid));
        }
    }

    /**
     * 更新用户的缓存
     * @param user 用户信息
     */
    public void setUser(AdminUserDetails user){
        if(user!=null&&StrUtil.isNotEmpty(user.getUuid())){
            refreshToken(user);
        }
    }

    /**
     * 拼接储存key
     *
     * @param uuid uuid
     * @return 拼接后key
     */
    private String getTokenKey(String uuid) {
        return userKey + ":" + uuid;
    }
}
