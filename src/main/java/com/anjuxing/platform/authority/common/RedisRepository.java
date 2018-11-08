package com.anjuxing.platform.authority.common;

import com.anjuxing.platform.authority.beans.CacheKeyConstants;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiongt
 * @Description
 */
@Component
public class RedisRepository {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    public void saveCache(String toSavedValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys){
        if(StringUtils.isEmpty(toSavedValue)) return;
        String cacheKey = generateCacheKey(prefix, keys);
        redisTemplate.opsForValue().set(cacheKey,toSavedValue,timeoutSeconds, TimeUnit.SECONDS);
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys) {
        String cacheKey = generateCacheKey(prefix, keys);
        return (String)redisTemplate.opsForValue().get(cacheKey);
    }

    /**
     * 缓存的key
     * @param prefix
     * @param keys
     * @return
     */
    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }
}
