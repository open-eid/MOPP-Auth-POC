package ee.ria.session.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import ee.ria.session.configuration.ConfigurationProvider;
import ee.ria.session.exception.SessionNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceHazelcastImpl implements SessionService {
    private static final Logger LOG = LoggerFactory.getLogger(SessionServiceHazelcastImpl.class);

    @Autowired
    private HazelcastInstance hazelcastInstance;

    private IMap<String, Session> sessionIMap() {
        return hazelcastInstance.getMap(ConfigurationProvider.MAP_SESSION_CACHE);
    }

    @Override
    public void send(Session session) {
        LOG.debug("Submitting the message id:{}", session.getSessionId());
        sessionIMap().set(session.getSessionId(), session);
    }

    @Override
    public Session receive(String sessionId) {
        LOG.debug("Polling message for recipient: {}", sessionId);
        Session session = sessionIMap().get(sessionId);
        if (session == null) {
            throw new SessionNotFoundException();
        }
        return session;
    }

}
