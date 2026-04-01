package net.parvkhandelwal.scheduler;


import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.service.AppCacheService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserScheduler {
    private final AppCacheService  appCacheService;




    @Scheduled(cron = "0 0/10 * ? * *")
    public void reloadCache(){
        appCacheService.loadAppCache();

    }



}
