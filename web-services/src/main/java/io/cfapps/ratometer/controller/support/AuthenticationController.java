package io.cfapps.ratometer.controller.support;

import io.cfapps.ratometer.annotations.web.Authenticate;
import io.cfapps.ratometer.config.MessageSourceConfig;
import io.cfapps.ratometer.config.support.JwtUserDetailsService;
import io.cfapps.ratometer.model.dto.UserDTO;
import io.cfapps.ratometer.util.security.EncryptionUtils;
import io.cfapps.ratometer.util.security.JwtTokenUtils;
import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.Optional;

@RestController
public class AuthenticationController implements ValidationErrorProcessor {

    private final JwtTokenUtils jwtTokenUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    public AuthenticationController(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtils jwtTokenUtils) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Validated(Authenticate.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return processValidationErrors(bindingResult);

        UserDetails userDetails = null;

        try {
            userDetails = jwtUserDetailsService.loadUserByUsername(userDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new ValidationException(MessageSourceConfig.getMessage("usr.err.invalid-credentials"));
        }

        if (userDetails.getUsername().equals(userDTO.getUsername()) &&
                EncryptionUtils.matchesBcryptHash(userDTO.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.ok().header("Authorization", jwtTokenUtils.generateToken(userDetails)).build();
        } else {
            throw new ValidationException(MessageSourceConfig.getMessage("usr.err.invalid-credentials"));
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("userAuthToken") String token,
                                           @RequestHeader("username") String username) {

        if (jwtTokenUtils.validateToken(token, username)) {
            return ResponseEntity
                    .ok(Response.of(HttpStatus.OK, MessageSourceConfig.getMessage("application.access.token-valid")));
        }

        return ResponseEntity.status(HttpStatus.GONE)
                .body(Response.of(HttpStatus.GONE, MessageSourceConfig.getMessage("application.access.token-expired")));
    }
}
