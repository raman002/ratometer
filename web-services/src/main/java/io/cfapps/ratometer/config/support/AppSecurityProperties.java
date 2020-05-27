package io.cfapps.ratometer.config.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.security")
public class AppSecurityProperties {

    private String jwtSecretKey;
    private String jwtTokenValidity;

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String getJwtTokenValidity() {
        return jwtTokenValidity;
    }

    public void setJwtTokenValidity(String jwtTokenValidity) {
        this.jwtTokenValidity = jwtTokenValidity;
    }
}
