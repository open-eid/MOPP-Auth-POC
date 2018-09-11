package ee.ria.relyingparty.messaging;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import ee.ria.relyingparty.authentication.HashType;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MessagingRequest {
    private String hash;
    private HashType hashType;
    private String sessionId;
}
