package net.parvkhandelwal.scheduler;


import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.repository.UserRepositoryImpl;
import net.parvkhandelwal.service.AppCacheService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class UserScheduler {
    private final UserRepositoryImpl userRepository;
    private final AppCacheService  appCacheService;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Scheduled(cron = "* * 9 * *  SUN")


    @Scheduled(cron = "0 0/10 * ? * *")
    public void reloadCache(){
        appCacheService.loadAppCache();

    }



}
