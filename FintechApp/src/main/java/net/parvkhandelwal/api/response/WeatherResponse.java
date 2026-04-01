package net.parvkhandelwal.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    private List<Weather> weather;
    private Main main;

    @Data
    public static class Main{
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;

    }
    @Data
    public static class Weather{

        private String main;
        private String description;

    }


}






