package net.parvkhandelwal.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.entity.AppCache;
import net.parvkhandelwal.repository.AppCacheRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppCacheService {
    private final AppCacheRepository repository;

    private final Map<String, AppCache> appCache = new ConcurrentHashMap<>();



    @PostConstruct
    public void loadAppCache(){

        List<AppCache> appCacheList=repository.findAll();
        for(AppCache cache:appCacheList){
            appCache.put(cache.getApiName(),cache);

        }
    }

    public String getApiKey(String apiName){
        try {
            AppCache cache=appCache.get(apiName);
            if(cache!=null){
                return cache.getApiKey();
            }

        }catch (Exception e){
            log.info("ApiKey error",e);
        }
        return null;


    }
}

