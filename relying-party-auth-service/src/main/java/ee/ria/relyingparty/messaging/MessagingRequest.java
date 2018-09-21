package ee.ria.relyingparty.messaging;

import lombok.Data;

@Data
public class MessagingRequest {
    private String hash;
    private String sessionId;
}
