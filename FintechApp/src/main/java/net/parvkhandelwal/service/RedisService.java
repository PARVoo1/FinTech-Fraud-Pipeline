package net.parvkhandelwal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String,String> redisTemplate;

    public void set(String key, Object value, long ttl) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key,json,ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }

    public <T> T get(String key, Class<T> entityClass) {

        try{
            Object o = redisTemplate.opsForValue().get(key);
            if(o == null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(o.toString(),entityClass);
        }catch (Exception e){
            log.error("Exception",e);
        }
        return null;


    }



}
