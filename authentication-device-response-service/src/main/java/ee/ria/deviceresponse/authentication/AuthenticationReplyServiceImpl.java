package ee.ria.deviceresponse.authentication;

import ee.ria.deviceresponse.authentication.client.ReplySessionServiceClient;
import ee.ria.deviceresponse.authentication.client.Session;
import ee.ria.deviceresponse.validation.RequestValidator;
import ee.ria.deviceresponse.validation.SignatureValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationReplyServiceImpl implements AuthenticationReplyService {

    @Autowired
    private ReplySessionServiceClient replySessionServiceClient;
    @Autowired
    private RequestValidator requestValidator;
    @Autowired
    private SignatureValidator signatureValidator;

    @Override
    public void processAuthenticationReply(AuthenticationReplyRequest request) {
        Session session = replySessionServiceClient.getSession(request.getSessionId());
        validateAndUpdateSession(request, session);
        replySessionServiceClient.updateSession(session);
    }

    private void validateAndUpdateSession(AuthenticationReplyRequest request, Session session) {
        boolean isRequestParametersValid = requestValidator.isRequestParametersValid(request, session);
        if (isRequestParametersValid) {
            boolean isSignatureValid = signatureValidator.isSignatureValid(request);
            if (isSignatureValid) {
                session.setResult(request.getResult());
                session.setCert(request.getCert());
                session.setSignature(request.getSignature().getValue());
            } else {
                session.setResult(ErrorCode.INVALID_SIGNATURE.name());
            }
        } else {
            session.setResult(ErrorCode.DATA_MISMATCH.name());
        }
    }
}
