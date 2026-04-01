package net.parvkhandelwal.service;

import lombok.RequiredArgsConstructor;
import net.parvkhandelwal.api.response.QuoteResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
@RequiredArgsConstructor
public class QuoteService {

    private final RestTemplate restTemplate;

    public QuoteResponse getQuote() {
        String finalApi="https://zenquotes.io/api/random";
         ResponseEntity<QuoteResponse[]>response=restTemplate.exchange(finalApi, HttpMethod.GET,null, QuoteResponse[].class);
         QuoteResponse[] body= response.getBody();
         if(body!=null&&body .length>0){

             return  body[0];

         }
         return null;
    }
}
