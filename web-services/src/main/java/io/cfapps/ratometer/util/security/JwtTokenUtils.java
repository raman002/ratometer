package io.cfapps.ratometer.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.cfapps.ratometer.config.support.AppSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

    private final AppSecurityProperties appSecurityProperties;

    private JwtTokenUtils(AppSecurityProperties appSecurityProperties) {
        this.appSecurityProperties = appSecurityProperties;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationLocalDateTimeFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(appSecurityProperties.getJwtSecretKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationLocalDateTimeFromToken(token).before(Date.from(Instant.now()));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String generateToken(Map<String, Object> claims, String subject) {

        Duration jwtTokenValidity = getJwtTokenValidity(appSecurityProperties.getJwtTokenValidity());

        Date expirationDate = Date.from(Instant.now().plusMillis(jwtTokenValidity.toMillis()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, appSecurityProperties.getJwtSecretKey()).compact();
    }

    public Boolean validateToken(String token, String username) {
        return getUsernameFromToken(token).equals(username) && !isTokenExpired(token);
    }

    public Duration getJwtTokenValidity(String tokenValidity) {
        String validity = StringUtils.defaultString(tokenValidity, "1hr");
        
        /*If validity is in minutes*/
        if (validity.endsWith("m")) {
            return Duration.ofMinutes(NumberUtils.toLong(validity.replace("m", ""), 0));
        }
        /*If validity is in hours*/
        else if (validity.endsWith("hr")) {
            return Duration.ofHours(NumberUtils.toLong(validity.replace("hr", ""), 0));
        }

        return null;
    }
}
