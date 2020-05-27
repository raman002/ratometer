package io.cfapps.ratometer.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = "io.cfapps.ratometer")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}