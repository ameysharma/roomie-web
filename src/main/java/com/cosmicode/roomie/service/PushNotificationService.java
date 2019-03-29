package com.cosmicode.roomie.service;

import com.cosmicode.roomie.config.ApplicationProperties;
import com.cosmicode.roomie.domain.Notification;
import com.cosmicode.roomie.domain.UserPreferences;
import com.cosmicode.roomie.domain.enumeration.NotificationType;
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

    private final Logger log = LoggerFactory.getLogger(PushNotificationService.class);
    private final ApplicationProperties applicationProperties;

    public PushNotificationService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public void send(Notification notification){
        try {
            UserPreferences preferences = notification.getRecipient().getConfiguration();

            if( (notification.getType().equals(NotificationType.APPOINTMENT) && !preferences.isAppointmentsNotifications()) ||
                (notification.getType().equals(NotificationType.TODO) && !preferences.isTodoListNotifications()) ||
                (notification.getType().equals(NotificationType.EXPENSE) && !preferences.isPaymentsNotifications()) ||
                (notification.getType().equals(NotificationType.EVENT) && !preferences.isCalendarNotifications())
                ){
                log.info("Notification disabled for {} for this user.", notification.getType().name());
                return;
            }

            JSONObject notificationJSON = new JSONObject();
            JSONObject dataJSON = new JSONObject();
            JSONObject requestJSON = new JSONObject();

            notificationJSON.put("title", notification.getTitle());
            notificationJSON.put("body", notification.getBody());

            dataJSON.put("id", notification.getId());
            dataJSON.put("type", notification.getType());
            dataJSON.put("roomie", notification.getRecipient().getId());
            dataJSON.put("entity", notification.getEntityId().toString());
            dataJSON.put("created", notification.getCreated().toString());

            requestJSON.put("to", notification.getRecipient().getMobileDeviceID());
            requestJSON.put("notification", notificationJSON);
            requestJSON.put("data", dataJSON);

            log.debug("Notification request {}", requestJSON.toString() );

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", "key=" + applicationProperties.getFirebaseCloudMessagingKey());
            httpHeaders.set("Content-Type", "application/json");
            HttpEntity<String> httpEntity = new HttpEntity<>(requestJSON.toString(), httpHeaders);
            String response = restTemplate.postForObject(applicationProperties.getFirebaseCloudMessagingUrl(),httpEntity,String.class);

            log.debug("Response from firebase: {}", response);
        } catch (JSONException e) {
            log.error("Error in request: {}", e.toString());
        } catch (NullPointerException e) {
            log.error("Invalid notification: {}", e.toString());
        }
    }

}