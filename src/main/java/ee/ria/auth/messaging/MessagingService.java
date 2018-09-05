package ee.ria.auth.messaging;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import ee.ria.auth.configuration.ConfigurationProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);

    @Autowired
    private ConfigurationProvider configurationProvider;

    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};
    private static final String TITLE = "Ria Digidoc";
    private static final String BODY = "Start authentication";
    private static final String MESSAGE_KEY = "message";
    private static final String NOTIFICATION_KEY = "notification";
    private static final String HASH_KEY = "hash";
    private static final String HASH_TYPE_KEY = "hashType";
    private static final String SESSION_ID_KEY = "sessionId";
    private static final String MESSAGING_DATA_KEY = "data";
    private static final String MESSAGING_BODY_KEY = "body";
    private static final String MESSAGING_TITLE_KEY = "title";


    public void message(MessagingRequest request) {
        try {
            JsonObject notificationMessage = buildNotificationMessage(request);
            LOGGER.info("FCM request body for message using common notification object:");
            prettyPrint(notificationMessage);
            sendMessage(notificationMessage);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong with request");
        }
    }

    private static void prettyPrint(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        LOGGER.info(gson.toJson(jsonObject) + "\n");
    }

    private JsonObject buildNotificationMessage(MessagingRequest request) {
        JsonObject jsonObj = new JsonObject();
        // client registration key is sent as token in the message to FCM server
        jsonObj.addProperty("token", getClientToken());

        JsonObject notification = new JsonObject();
        notification.addProperty(MESSAGING_BODY_KEY, BODY);
        notification.addProperty(MESSAGING_TITLE_KEY, TITLE);
        jsonObj.add(NOTIFICATION_KEY, notification);
        JsonObject data = new JsonObject();
        data.addProperty(HASH_KEY, request.getHash());
        data.addProperty(HASH_TYPE_KEY, request.getHashType());
        data.addProperty(SESSION_ID_KEY, request.getSessionId());

        jsonObj.add(MESSAGING_DATA_KEY, data);

        JsonObject message = new JsonObject();
        message.add(MESSAGE_KEY, jsonObj);

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
            throw new RuntimeException("Something went wrong with opening service account file");
        }
    }

    private void sendMessage(JsonObject fcmMessage) throws IOException {
        HttpURLConnection connection = getConnection();
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(fcmMessage.toString());
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            String response = inputStreamToString(connection.getInputStream());
            LOGGER.info("Message sent to Firebase for delivery, response:");
            LOGGER.info(response);
        } else {
            LOGGER.info("Unable to send message to Firebase:");
            String response = inputStreamToString(connection.getErrorStream());
            LOGGER.info(response);
            //TODO: Handle response
        }
    }

    private HttpURLConnection getConnection() throws IOException {
        URL url = new URL(BASE_URL + configurationProvider.getFireBaseEndpoint());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        return httpURLConnection;
    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

}
