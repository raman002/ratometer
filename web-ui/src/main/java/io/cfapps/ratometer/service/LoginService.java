package io.cfapps.ratometer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.config.ApplicationProperties;
import io.cfapps.ratometer.model.dto.LoginDTO;
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

@Service
public class LoginService {

    private final ApplicationProperties applicationProperties;
    private final ObjectMapper objectMapper;

    public LoginService(ApplicationProperties applicationProperties, ObjectMapper objectMapper) {
        this.applicationProperties = applicationProperties;
        this.objectMapper = objectMapper;
    }

    public Response<Header[]> authenticate(LoginDTO loginDTO) throws IOException, InterruptedException {
        Response<Header[]> response = new Response<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(applicationProperties.getApiBaseURL() + "/authenticate");
                httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(loginDTO), ContentType.APPLICATION_JSON));

            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            response.setCode(httpResponse.getStatusLine().getStatusCode());
            response.setResult(httpResponse.getAllHeaders());
        }
        return response;
    }
}
