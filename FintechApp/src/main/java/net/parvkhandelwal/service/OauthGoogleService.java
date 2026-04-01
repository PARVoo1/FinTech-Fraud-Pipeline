package net.parvkhandelwal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.entity.User;
import net.parvkhandelwal.repository.UserRepository;
import net.parvkhandelwal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OauthGoogleService {

    private final RestTemplate restTemplate;
    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String clientSecret;

    public ResponseEntity<Object>  login(String code) {

        try{
            String tokenEndPoint="https://oauth2.googleapis.com/token";



            MultiValueMap<String, String> params=new LinkedMultiValueMap<>();
            params.add("code",code);
            params.add("client_id",clientId);
            params.add("client_secret",clientSecret);
            params.add("redirect_uri","https://developers.google.com/oauthplayground");
            params.add("grant_type","authorization_code");
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request=new HttpEntity<>(params,headers);

            ResponseEntity<Map> tokenResponse=restTemplate.postForEntity(
                    tokenEndPoint,
                    request,
                    Map.class);

            String accessToken = (String) tokenResponse.getBody().get("access_token");

            String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";


            HttpHeaders userInfoHeaders = new HttpHeaders();
            userInfoHeaders.setBearerAuth(accessToken);
            HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoHeaders);


            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.GET,
                    userInfoRequest,
                    Map.class
            );
            if(userInfoResponse.getStatusCode()== HttpStatus.OK){
                Map<String,Object> userInfo=userInfoResponse.getBody();

                String email=(String)userInfo.get("email");

                registerUserIfNotFound(email);


                String jwtToken=jwtUtil.generateToken(email);
                return ResponseEntity.ok(Map.of("token", jwtToken));



            }

        }catch(Exception e){
            log.error("Exception occurred",e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();


    }

    private void registerUserIfNotFound(String email) {
        try{
            userDetailService.loadUserByUsername(email);
        }
        catch (UsernameNotFoundException e){
            User user=new User();
            user.setEmail(email);
            user.setUserName(email);
            user.setUserPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setRoles(Collections.singletonList("USER"));
            userRepository.save(user);

        }
    }


}
