package io.ordi.fcprojectsns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FcProjectSnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcProjectSnsApplication.class, args);
    }

}
