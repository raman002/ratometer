package io.cfapps.ratometer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.config.ApplicationProperties;
import io.cfapps.ratometer.model.dto.CategoriesDTO;
import io.cfapps.ratometer.model.dto.MemberDTO;
import io.cfapps.ratometer.model.dto.TeamDTO;
import io.cfapps.ratometer.model.dto.UserDetailsDTO;
import io.cfapps.ratometer.util.web.Response;
import org.apache.catalina.User;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class DashboardService {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public DashboardService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    public Response<List<String>> loadUserRoles(UserDetailsDTO userDetailsDTO) throws IOException {

        Response<List<String>> response;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(applicationProperties.getApiBaseURL() + "/user-roles/get-by-user");

            httpGet.setHeader("Authorization", userDetailsDTO.getAuthToken());
            httpGet.setHeader("username", userDetailsDTO.getUsername());

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            response = objectMapper.readValue(responseBody, new TypeReference<Response<List<String>>>() {});
        }
        return response;
    }

    public Response<List<TeamDTO>> fetchTeams(UserDetailsDTO userDetailsDTO) throws IOException {
        Response<List<TeamDTO>> response = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(applicationProperties.getApiBaseURL() + "/teams/get-all");

            httpGet.setHeader("Authorization", userDetailsDTO.getAuthToken());

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            response = objectMapper.readValue(responseBody, new TypeReference<Response<List<TeamDTO>>>() {
            });
        }
        return response;
    }

    public Response<List<MemberDTO>> fetchMembers(UserDetailsDTO userDetailsDTO, boolean fetchUnassigned) throws IOException {
        Response<List<MemberDTO>> response;
        StringBuilder URL = new StringBuilder(applicationProperties.getApiBaseURL());

        if (fetchUnassigned) {
            URL.append("/users/get-unassigned-members");
        }
        else {
            URL.append("/users/get-all-team-members");
        }


        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(URL.toString());

            httpGet.setHeader("Authorization", userDetailsDTO.getAuthToken());
            httpGet.setHeader("username", userDetailsDTO.getUsername());

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            response = objectMapper
                    .readValue(responseBody, new TypeReference<Response<List<MemberDTO>>>() {
                    });
        }
        return response;
    }

    public Response<List<CategoriesDTO>> fetchCategories(UserDetailsDTO userDetailsDTO) throws IOException {
        Response<List<CategoriesDTO>> response = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(applicationProperties.getApiBaseURL() + "/categories/get-all");

            httpGet.setHeader("Authorization", userDetailsDTO.getAuthToken());
            httpGet.setHeader("username", userDetailsDTO.getUsername());

            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            response = objectMapper.readValue(responseBody, new TypeReference<Response<List<CategoriesDTO>>>() {
            });
        }
        return response;
    }

    public Response<?> assignTeams(String requestBody, UserDetailsDTO userDetailsDTO) throws IOException {
        Response<?> response = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(applicationProperties.getApiBaseURL() + "/team/assign-teams");
            httpPost.setEntity(new StringEntity(requestBody));

            httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            httpPost.setHeader("Authorization", userDetailsDTO.getAuthToken());

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            response = objectMapper.readValue(responseBody, new TypeReference<Response<List<CategoriesDTO>>>() {
            });
        }
        return response;
    }
}
