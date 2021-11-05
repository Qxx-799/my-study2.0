package com.qxx.thirdservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;

@Service
@SessionScope
public class TokenTaskService {

    @Autowired
    RestTemplate restTemplate;

    public String access_token = "";
    public String refresh_token = "";

    public String getDta(String code){
        if ("".equals(access_token) && code != null) {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("code", code);
            map.add("client_id", "authService");
            map.add("client_secret", "123456");
            map.add("redirect_uri", "http://localhost:8083/index.html");
            map.add("grant_type", "authorization_code");
            Map<String, String> resp = restTemplate.postForObject("http://localhost:8081/oauth/token", map, Map.class);
            access_token = resp.get("access_token");
            refresh_token = resp.get("refresh_token");
            return loadDataFromResServer();
        } else {
            return loadDataFromResServer();
        }
    }

    private String loadDataFromResServer(){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + access_token);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8082/admin/user", HttpMethod.GET, httpEntity, String.class);
            return entity.getBody();
        } catch (RestClientException e) {
            return "未加载";
        }
    }

    @Scheduled(cron = "0 55 0/1 * * ?")
    public void tokenTask(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "authService");
        map.add("client_secret", "123456");
        map.add("refresh_token", refresh_token);
        map.add("grant_type", "refresh_token");
        Map<String, String> resp = restTemplate.postForObject("http://localhost:8081/oauth/token", map, Map.class);
        access_token = resp.get("access_token");
        refresh_token = resp.get("refresh_token");
    }
}
