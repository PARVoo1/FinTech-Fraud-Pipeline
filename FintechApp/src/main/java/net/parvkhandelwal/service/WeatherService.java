package net.parvkhandelwal.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    @Value("${app.custom.base.url}")
    private  String baseUrl;


    private final RestTemplate restTemplate;
    private final AppCacheService appCacheService;
    private final RedisService redisService;

    public WeatherResponse response(String city) {

        WeatherResponse weatherResponse = redisService.get("weather:" + city, WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }else {
            String finalApi=baseUrl+"?q=" + URLEncoder.encode(city, StandardCharsets.UTF_8)+ "&appid="+appCacheService.getApiKey("OPENWEATHER") +"&units=metric";

            ResponseEntity<WeatherResponse> response=restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather:" + city, body, 300L);
            }

            return body;

        }


    }




}
