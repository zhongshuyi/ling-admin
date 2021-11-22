package com.mall.common.util.ip;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


/**
 * 地址工具类.
 *
 * @author 钟舒艺
 **/
@Slf4j
@SuppressWarnings("unused")
public final class AddressUtils {
    /**
     * IP地址查询网站.
     */
    public static final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp";

    /**
     * 未知地址.
     */
    public static final String UNKNOWN = "XX XX";


    private AddressUtils() {
    }

    /**
     * 获取ip的地理位置.
     *
     * @param ip ip地址
     * @return 地理位置
     */
    public static String getAddressByIp(final String ip) {
        // 内网不查询
        if (NetUtil.isInnerIP(ip)) {
            return "内网IP";
        }
        try {
            String rspStr = HttpUtil.get(
                    IP_URL + "?ip=" + ip + "&json=true", CharsetUtil.CHARSET_GBK);
            if (StrUtil.isEmpty(rspStr)) {
                log.error("获取地理位置异常 {}", ip);
                return UNKNOWN;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(rspStr);
            String region = root.get("pro").asText();
            String city = root.get("city").asText();
            return String.format("%s %s", region, city);
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return UNKNOWN;
    }
}