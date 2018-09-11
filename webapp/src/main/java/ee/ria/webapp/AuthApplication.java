package ee.ria.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ee.ria.relyingparty", "ee.ria.account", "ee.ria.deviceresponse", "ee.ria.webapp"})

public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
