package io.cfapps.ratometer.config.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.util.security.JwtTokenUtils;
import io.cfapps.ratometer.util.web.Response;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    	String requestURI = httpServletRequest.getRequestURI();
    	
    	/* If the request is coming from one of the below URIs then 
    	 * we will allow them as we don't require authentication for them.
    	*/
    	if (requestURI.contains("/authenticate")) {
    		filterChain.doFilter(httpServletRequest, httpServletResponse);
    		return;
    	}
    	
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            prepareErrorResponse(httpServletResponse, Response.of(HttpStatus.BAD_REQUEST, "'Authorization' header is required!"));
        }
        
        try {
        	 String username = jwtTokenUtils.getUsernameFromToken(token);
             
             if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

     			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

     			/* if token is valid configure Spring Security to manually set
     			   authentication
     			 */
     			if (jwtTokenUtils.validateToken(token, userDetails.getUsername())) {

     				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
     						userDetails, null, userDetails.getAuthorities());
     				usernamePasswordAuthenticationToken
     						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
     				// After setting the Authentication in the context, we specify
     				// that the current user is authenticated. So it passes the
     				// Spring Security Configurations successfully.
     				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
     			}
			}
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			
		} catch (Exception e) {
			if (e instanceof ExpiredJwtException) {
				log.error(e.getMessage(), e);
				prepareErrorResponse(httpServletResponse, Response.of(HttpStatus.UNAUTHORIZED, "Token is expired!"));
			}
		}
    }

	private void prepareErrorResponse(HttpServletResponse httpServletResponse, Response<String> response) {
		try(OutputStream stream = httpServletResponse.getOutputStream()) {
			stream.write(objectMapper.writeValueAsBytes(response));
			httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
