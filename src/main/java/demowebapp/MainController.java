package demowebapp;

import demowebapp.authentication.AuthenticationService;
import demowebapp.authentication.VerificationResponse;
import demowebapp.authentication.VerificationService;
import demowebapp.authentication.client.AuthenticationStatusResponse;
import demowebapp.authentication.exception.SignatureVerificationException;
import demowebapp.authentication.util.CertificateUtil;
import demowebapp.authentication.validator.ValidatorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {
    private static final String RESULT_OK = "OK";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private ValidatorService validatorService;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/verification", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String authenticate(@RequestParam String nationalIdentityNumber, Model model) {
        VerificationResponse verificationResponse = verificationService.getVerificationResponse();
        model.addAttribute("verificationCode", verificationResponse.getVerificationCode());
        model.addAttribute("hash", verificationResponse.getHash());
        model.addAttribute("nationalIdentityNumber", nationalIdentityNumber);
        return "verification";
    }

    @RequestMapping(value = "/validate")
    public ModelAndView validate(@RequestParam String signature, @RequestParam String certificate, @RequestParam String hash, ModelMap model) {
        ModelAndView mav = new ModelAndView();
        model.addAttribute("signature", signature);
        model.addAttribute("certificate", certificate);
        model.addAttribute("hash", hash);
        boolean isSignatureValid = false;
        try {
            isSignatureValid = validatorService.isSignatureValid(certificate, signature, hash);
        } catch (SignatureVerificationException e) {
            //Do nothing
        }
        try {
            List<String> commonNameValues = CertificateUtil.getCommonNameValues(certificate);
            model.addAttribute("personName", CertificateUtil.getFullNameFromCommonName(commonNameValues));
            model.addAttribute("nationalIdentityNumber", commonNameValues.get(2));
        } catch (Exception e) {
            //Do nothing
        }
        if (isSignatureValid) {
            model.addAttribute("verificationResult", "OK");
        } else {
            model.addAttribute("verificationResult", "NOK");
        }
        String viewName = "response";
        mav.setViewName(viewName);
        return mav;
    }

    @RequestMapping(value = "/authentication")
    public ModelAndView authentication(@RequestParam String hash, @RequestParam String nationalIdentityNumber, ModelMap model) throws Exception {
        AuthenticationStatusResponse response = authenticationService.authenticate(hash, nationalIdentityNumber);
        model.addAttribute("signature", response.getSignature());
        model.addAttribute("certificate", response.getCert());
        try {
            List<String> commonNameValues = CertificateUtil.getCommonNameValues(response.getCert());
            model.addAttribute("personName", CertificateUtil.getFullNameFromCommonName(commonNameValues));
            model.addAttribute("nationalIdentityNumber", commonNameValues.get(2));
        } catch (Exception e) {
            //Do nothing
        }
        if (!RESULT_OK.equals(response.getResult())) {
            model.addAttribute("result", response.getResult());
        }
        model.addAttribute("hash", hash);
        ModelAndView mav = new ModelAndView();
        String viewName = "response";
        mav.setViewName(viewName);
        return mav;
    }

}
