package io.cfapps.ratometer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.config.ApplicationProperties;
import io.cfapps.ratometer.model.dto.LoginDTO;
import io.cfapps.ratometer.model.dto.MemberDTO;
import io.cfapps.ratometer.model.dto.RegisterDTO;
import io.cfapps.ratometer.model.dto.TeamDTO;
import io.cfapps.ratometer.util.web.Response;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class RegistrationService {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public RegistrationService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    public Response<MemberDTO> registerMember(RegisterDTO registerDTO) throws IOException {
        Response<MemberDTO> response;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(applicationProperties.getApiBaseURL() + "/users/create-user");
            httpPost.setEntity(new StringEntity(objectMapper
                    .writeValueAsString(registerDTO), ContentType.APPLICATION_JSON));

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

            String responseBody = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            response = objectMapper.readValue(responseBody, new TypeReference<Response<MemberDTO>>() {
            });
        }
        return response;
    }
}
