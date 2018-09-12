package ee.ria.session.service;

public interface SessionService {

    void send(Session session);

    Session receive(String sessionId);
}
