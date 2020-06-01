package io.cfapps.ratometer.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cfapps.ratometer.annotations.web.Authenticate;
import io.cfapps.ratometer.annotations.web.Create;
import io.cfapps.ratometer.annotations.web.Update;

import javax.validation.constraints.NotNull;
import java.util.*;

public class UserDTO {
    @NotNull(groups = Update.class)
    private UUID uuid;

    @NotNull(groups = {Create.class, Authenticate.class})
    private String username;

    @NotNull(groups = Create.class)
    private String email;

    @NotNull(groups = Create.class)
    private String fullName;

    @NotNull(groups = {Create.class, Authenticate.class})
    private String password;

    private String contactNo;

    private Set<String> teams;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Set<String> getTeams() {
        return teams;
    }

    public void setTeams(Set<String> teams) {
        this.teams = teams;
    }
}
