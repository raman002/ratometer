package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.model.dto.LoginDTO;
import io.cfapps.ratometer.model.dto.UserDetailsDTO;
import io.cfapps.ratometer.service.DashboardService;
import io.cfapps.ratometer.service.LoginService;
import io.cfapps.ratometer.util.web.Response;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

import static io.cfapps.ratometer.config.MessageSourceConfig.getMessage;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final LoginService loginService;
    private final DashboardService dashboardService;

    public LoginController(LoginService loginService, DashboardService dashboardService) {
        this.loginService = loginService;
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ModelAndView login(RedirectView redirectView, @RequestParam Map<String, Object> requestParams) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (redirectView.isRedirectView()) {
            modelAndView.addObject("title", "Login");
            handleRedirect(modelAndView, requestParams);
        }

        return modelAndView;
    }

    private void handleRedirect(ModelAndView modelAndView, Map<String, Object> requestParams) {
        modelAndView.addAllObjects(requestParams);
    }

    @PostMapping("/submit")
    public RedirectView loginSubmit(@ModelAttribute("loginDTO") LoginDTO loginDTO, RedirectView redirectView,
                                    RedirectAttributes attributes, HttpSession session) {
        Response<Header[]> response = null;

        try {
            response = loginService.authenticate(loginDTO);
        } catch (IOException | InterruptedException e) {
            log.error("Something went wrong while connecting to server!", e);
        }

        if (response == null) {
            prepareErrorMessage(attributes, getMessage("usr.err.unknown-error"));
            return new RedirectView("/login");
        }

        String token = Stream.of(response.getResult()).filter(e -> e.getName().equals("Authorization"))
                .findFirst().get().getValue();
        int statusCode = response.getCode();

        // If status code is 400 then it means the credentials are rejected.
        if (statusCode == HttpStatus.OK.value()) {
            prepareUserDetails(session, loginDTO, token);
            UserDetailsDTO userDetails = (UserDetailsDTO) session.getAttribute("userDetails");

            if (userDetails.getRoles().contains("SUPER_ADMIN"))
                return new RedirectView("/dashboard?teams");
            else
                return new RedirectView("/dashboard?intro");
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            prepareErrorMessage(attributes, getMessage("usr.err.invalid-credentials"));
        } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            log.error("Login Failed, Something went wrong! ");
            prepareErrorMessage(attributes, getMessage("usr.err.unknown-error"));
        }

        return new RedirectView("/login");
    }

    private void prepareUserDetails(HttpSession session, LoginDTO loginDTO, String authorization) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        userDetailsDTO.setAuthToken(authorization);
        userDetailsDTO.setUsername(loginDTO.getUsername());
        try {
            userDetailsDTO.setRoles(dashboardService.loadUserRoles(userDetailsDTO).getResult());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        session.setAttribute("userDetails", userDetailsDTO);
    }

    private void prepareErrorMessage(RedirectAttributes attributes, String message) {
        attributes.addAttribute("showAlert", true);
        attributes.addAttribute("alertClass", "alert-danger show");
        attributes.addAttribute("message", message);
    }
}
