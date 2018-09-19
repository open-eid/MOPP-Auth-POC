package ee.ria.deviceresponse.authentication.client;

public interface ReplySessionServiceClient {
    Session getSession(String sessionId);
    void updateSession(Session request);
}
