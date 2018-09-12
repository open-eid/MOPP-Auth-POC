package ee.ria.session.controller;

import ee.ria.session.service.Session;
import ee.ria.session.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void send(@RequestBody SessionRequest request) {
        sessionService.send(map(request));
    }

    @RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
    public Session receiveSession(@PathVariable("sessionId") String sessionId) {
        return sessionService.receive(sessionId);
    }

    private static Session map(SessionRequest sessionRequest) {
        Session session = new Session();
        session.setSessionId(sessionRequest.getSessionId());
        session.setNationalIdentityNumber(sessionRequest.getNationalIdentityNumber());
        session.setResult(sessionRequest.getResult());
        session.setSignature(sessionRequest.getSignature());
        session.setCert(sessionRequest.getCert());
        return session;
    }
}
