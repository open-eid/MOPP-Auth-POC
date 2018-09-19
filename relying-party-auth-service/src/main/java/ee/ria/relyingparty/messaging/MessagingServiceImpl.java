package ee.ria.relyingparty.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import ee.ria.relyingparty.configuration.ConfigurationProvider;
import ee.ria.relyingparty.exception.FireBaseConnectionException;
import ee.ria.relyingparty.exception.GoogleServiceAccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class MessagingServiceImpl implements MessagingService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingServiceImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigurationProvider configurationProvider;

    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};
    private static final String TITLE = "Ria Digidoc";
    private static final String BODY = "Start authentication";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    public void message(MessagingRequest request) {
        try {
            Message notificationMessage = buildNotificationMessage(request);
            LOGGER.info("FCM request body for message using common notification object:");
            prettyPrint(notificationMessage);
            sendMessage(notificationMessage);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong with request");
        }
    }

    private static void prettyPrint(Message jsonObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        LOGGER.info(indented + "\n");
    }

    private Message buildNotificationMessage(MessagingRequest request) {
        Message message = new Message();
        Message.MessageRoot messageRoot = message.new MessageRoot();
        Message.MessageRoot.Data data = messageRoot.new Data();
        Message.MessageRoot.Notification notification = messageRoot.new Notification();

        messageRoot.setToken(getClientToken());

        data.setHash(request.getHash());
        data.setHashType(request.getHashType());
        data.setSessionId(request.getSessionId());
        messageRoot.setData(data);

        notification.setBody(BODY);
        notification.setTitle(TITLE);
        messageRoot.setNotification(notification);
        message.setMessage(messageRoot);
        return message;

    }

    private String getClientToken() {
        return "c8c0FgG7xOw:APA91bGDafsoV2QNUA9a_w7YhlhvQ9iYoiNt4oagvWNBPkeMNZ7ydv02PyvQTnQHviziEXX22wHPEDb_a_3olB5Qr8yeOe_IYcESXlrX5mYUaY181Yet6Ta_JTdT7H6lvZq91iuBFGJHxtmqWUKvK7hHZQGGP9IFGQ";
    }

    private String getAccessToken() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            GoogleCredential googleCredential = GoogleCredential
                    .fromStream(classLoader.getResourceAsStream("service-account.json"))
                    .createScoped(Arrays.asList(SCOPES));
            googleCredential.refreshToken();
            return googleCredential.getAccessToken();
        } catch (IOException e) {
            throw new GoogleServiceAccountNotFoundException();
        }
    }

    private void sendMessage(Message fcmMessage) {
        try {
            String firebaseUrl = BASE_URL + configurationProvider.getFireBaseEndpoint();
            ResponseEntity<MessagingResponse> responseEntity = restTemplate.exchange(firebaseUrl, HttpMethod.POST, formHttpEntity(fcmMessage), MessagingResponse.class);
            LOGGER.info("Message sent to Firebase for delivery, response:");
            if (responseEntity.getBody() != null) {
                LOGGER.info(responseEntity.getBody().getName());
            }
        } catch (Exception e) {
            LOGGER.info("Unable to send message to Firebase");
            throw new FireBaseConnectionException(e.getMessage());
        }
    }

    private HttpEntity<?> formHttpEntity(Message message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, "Bearer " + getAccessToken());
        headers.add(CONTENT_TYPE_HEADER, "application/json; UTF-8");
        return new HttpEntity<>(message, headers);
    }

}
