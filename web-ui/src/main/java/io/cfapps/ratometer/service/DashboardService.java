package io.cfapps.ratometer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.config.ApplicationProperties;
import io.cfapps.ratometer.model.dto.CategoriesDTO;
import io.cfapps.ratometer.model.dto.UserDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public DashboardService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    public HttpResponse<String> loadUserRoles(UserDetailsDTO userDetailsDTO) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", userDetailsDTO.getAuthToken())
                .header("username", userDetailsDTO.getUsername())
                .GET()
                .uri(URI.create(applicationProperties.getApiBaseURL() + "/user-roles/get-by-user"))
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        return httpResponse;
    }

    public HttpResponse<String> fetchTeams(UserDetailsDTO userDetailsDTO) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", userDetailsDTO.getAuthToken())
                .GET()
                .uri(URI.create(applicationProperties.getApiBaseURL() + "/teams/get-all"))
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        return httpResponse;
    }

    public HttpResponse<String> fetchMembers(UserDetailsDTO userDetailsDTO) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", userDetailsDTO.getAuthToken())
                .header("username", userDetailsDTO.getUsername())
                .GET()
                .uri(URI.create(applicationProperties.getApiBaseURL() + "/users/get-all-team-members"))
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        return httpResponse;
    }

    public HttpResponse<String> fetchCategories(UserDetailsDTO userDetailsDTO) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", userDetailsDTO.getAuthToken())
                .uri(URI.create(applicationProperties.getApiBaseURL() + "/categories/get-all"))
                .build();

        HttpResponse<String> httpResponse = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()
                .send(httpRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        return httpResponse;
    }
}
