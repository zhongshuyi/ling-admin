package com.mall.framework.interceptor.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.filter.RepeatedlyRequestWrapper;
import com.mall.common.util.RedisUtils;
import com.mall.framework.interceptor.BaseRepeatSubmitInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 *
 * @author ruoyi
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SuppressWarnings("unused")
public class SameUrlDataInterceptor extends BaseRepeatSubmitInterceptor implements Serializable {

    private static final long serialVersionUID = 3670730997054064652L;

    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    /**
     * 令牌自定义标识
     */
    @Value("${token.token-header}")
    private String header;


    /**
     * 间隔时间，单位:秒 默认10秒
     * <p>
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    private Long intervalTime = 10L;

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";


    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request) throws JsonProcessingException {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = IoUtil.readUtf8(repeatedlyRequest.getInputStream());
        }

        // body参数为空，获取Parameter的数据
        if (Validator.isEmpty(nowParams)) {
            nowParams = new ObjectMapper().writeValueAsString(request.getParameterMap());
        }
        Map<String, Object> nowDataMap = new HashMap<>(2);
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();

        // 唯一值（没有消息头则使用请求地址）
        String submitKey = request.getHeader(header);
        if (Validator.isEmpty(submitKey)) {
            submitKey = url;
        }

        // 唯一标识（指定key + 消息头）
        String cacheRepeatKey = REPEAT_SUBMIT_KEY + submitKey;

        Map<String, Object> session = RedisUtils.getCacheObject(cacheRepeatKey);
        if (session != null) {
            if (session.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) session.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<>(2);
        cacheMap.put(url, nowDataMap);
        RedisUtils.setCacheObject(cacheRepeatKey, cacheMap, intervalTime, TimeUnit.SECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < (this.intervalTime * GlobalConstants.SECONDS);
    }
}
