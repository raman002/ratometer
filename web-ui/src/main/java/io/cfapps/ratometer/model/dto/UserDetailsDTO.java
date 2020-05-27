package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.List;

public class UserDetailsDTO implements Serializable {

    private String authToken;
    private String username;
    private List<String> roles;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
