package ee.ria.common.client;

public interface SessionServiceClient {
    Session getSession(String sessionId);
    void createNewSession(Session request);
    void deleteCompleteSession(String sessionId);
    void updateSession(Session request);

}
