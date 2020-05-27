package io.cfapps.ratometer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.config.ApplicationProperties;
import io.cfapps.ratometer.model.dto.LoginDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class LoginService {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public LoginService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    public HttpResponse<String> authenticate(LoginDTO loginDTO) throws IOException, InterruptedException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(loginDTO), StandardCharsets.UTF_8))
                .uri(URI.create(applicationProperties.getApiBaseURL() + "/authenticate"))
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));


      return httpResponse;
    }
}
