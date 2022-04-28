package com.mall.framework.interceptor.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.filter.RepeatedlyRequestWrapper;
import com.mall.common.util.RedisUtils;
import com.mall.framework.config.CustomConfig;
import com.mall.framework.interceptor.BaseRepeatSubmitInterceptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 判断请求url和数据是否和上一次相同.
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内.
 *
 * @author ruoyi
 */
@Slf4j
@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class SameUrlDataInterceptor extends BaseRepeatSubmitInterceptor implements Serializable {


    /**
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * Jackson序列化.
     */
    private final ObjectMapper objectMapper;

    /**
     * 防重提交 redis key.
     */
    private static final String REPEAT_SUBMIT_KEY = "repeat_submit:";
    /**
     * 参数列表的键.
     */
    private static final String REPEAT_PARAMS = "repeatParams";
    /**
     * 提交时间.
     */
    private static final String REPEAT_TIME = "repeatTime";

    private static final long serialVersionUID = 3670730997054064652L;
    /**
     * 间隔时间，单位:秒 默认10秒.
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据.
     */
    private Long intervalTime = 10L;

    /**
     * 获取间隔时间.
     *
     * @return 间隔时间.
     */
    public Long getIntervalTime() {
        return intervalTime;
    }

    /**
     * 设置间隔时间.
     *
     * @param intervalTime 间隔时间
     */
    public void setIntervalTime(final Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    @Override
    public final boolean isRepeatSubmit(
            final HttpServletRequest request
    ) throws JsonProcessingException {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            final RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = IoUtil.readUtf8(repeatedlyRequest.getInputStream());
        }

        // body参数为空，获取Parameter的数据
        if (Validator.isEmpty(nowParams)) {
            nowParams = objectMapper.writeValueAsString(request.getParameterMap());
        }
        final Map<String, Object> nowDataMap = new HashMap<>(2);
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放cache的key值）
        final String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String submitKey = request.getHeader(config.getToken().getTokenHeader());
        if (Validator.isEmpty(submitKey)) {
            submitKey = url;
        }

        // 唯一标识（指定key + 消息头）
        final String cacheRepeatKey = REPEAT_SUBMIT_KEY + submitKey;

        final Map<String, Object> session = RedisUtils.getCacheObject(cacheRepeatKey);
        if (session != null && session.containsKey(url)) {
            final Map<String, Object> preDataMap =
                    Convert.convert(new TypeReference<Map<String, Object>>() {
                    }, session.get(url));
            if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                return true;
            }
        }
        final Map<String, Object> cacheMap = new HashMap<>(2);
        cacheMap.put(url, nowDataMap);
        RedisUtils.setCacheObject(cacheRepeatKey, cacheMap, intervalTime, TimeUnit.SECONDS);
        return false;
    }

    /**
     * 判断参数是否相同.
     */
    private boolean compareParams(
            final Map<String, Object> nowMap,
            final Map<String, Object> preMap
    ) {
        final String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        final String preParams = (String) preMap.get(REPEAT_PARAMS);
        if (nowParams == null || preParams == null) {
            return false;
        }
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间.
     */
    private boolean compareTime(
            final Map<String, Object> nowMap,
            final Map<String, Object> preMap
    ) {
        final Long time1 = (Long) nowMap.get(REPEAT_TIME);
        final Long time2 = (Long) preMap.get(REPEAT_TIME);
        if (time1 == null || time2 == null) {
            return false;
        }
        return (time1 - time2) < (this.intervalTime * GlobalConstants.SECONDS);
    }
}
