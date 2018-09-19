package ee.ria.relyingparty.messaging;

import lombok.Data;

import ee.ria.relyingparty.authentication.HashType;

@Data
public class MessagingRequest {
    private String hash;
    private HashType hashType;
    private String sessionId;
}
