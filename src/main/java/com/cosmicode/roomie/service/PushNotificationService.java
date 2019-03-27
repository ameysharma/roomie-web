package com.cosmicode.roomie.service;

import com.cosmicode.roomie.config.ApplicationProperties;
import com.cosmicode.roomie.service.dto.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class PushNotificationService {

    private final Logger log = LoggerFactory.getLogger(RoomService.class);
    private final ApplicationProperties applicationProperties;

    public PushNotificationService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void send(NotificationDTO notification){
        try {
            String deviceToken = "test";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + applicationProperties.getFirebaseCloudMessagingKey());
            httpHeaders.set("Content-Type", "application/json");
            JSONObject msg = new JSONObject();
            JSONObject json = new JSONObject();

            msg.put("title", "Title");
            msg.put("body", "Message");
            msg.put("notificationType", "Test");

            json.put("data", msg);
            json.put("to", deviceToken);

            HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
            String response = restTemplate.postForObject(applicationProperties.getFirebaseCloudMessagingUrl(),httpEntity,String.class);

            log.debug("Response from firebase: {}", response);
        } catch (JSONException e) {
            e.printStackTrace();
            log.error("Error in request: {}", e.getMessage());
        }
    }

}
