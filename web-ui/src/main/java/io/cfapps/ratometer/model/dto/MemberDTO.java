package io.cfapps.ratometer.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class MemberDTO implements Serializable {

    private String username;
    private String fullName;
    private String email;
    private UUID uuid;
    private List<String> teams;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }
}
