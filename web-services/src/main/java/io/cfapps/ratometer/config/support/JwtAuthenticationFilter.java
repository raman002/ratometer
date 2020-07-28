package io.cfapps.ratometer.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.util.security.JwtTokenUtils;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final ObjectMapper objectMapper;
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtAuthenticationFilter(ObjectMapper objectMapper, JwtTokenUtils jwtTokenUtils,
                                   JwtUserDetailsService jwtUserDetailsService) {
        super();
        this.objectMapper = objectMapper;
        this.jwtTokenUtils = jwtTokenUtils;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

    	filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void prepareErrorResponse(HttpServletResponse httpServletResponse, Response<String> response) {
        try (OutputStream stream = httpServletResponse.getOutputStream()) {
            stream.write(objectMapper.writeValueAsBytes(response));
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
