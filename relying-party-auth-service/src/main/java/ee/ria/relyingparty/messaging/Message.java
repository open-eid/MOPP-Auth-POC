package ee.ria.relyingparty.messaging;

import lombok.Data;


@Data
public class Message {

    private MessageRoot message;

    @lombok.Data
    class MessageRoot {
        private String token;
        private Notification notification;
        private Data data;

        @lombok.Data
        class Notification {
            private String body;
            private String title;
        }

        @lombok.Data
        class Data {
            private String hash;
            private String sessionId;
        }
    }
}

