package io.cfapps.ratometer.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginDTO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
