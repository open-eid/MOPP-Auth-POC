package ee.ria.auth.messaging;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MessagingRequest {
    private String hash;
    private String hashType;
    private String sessionId;
}
