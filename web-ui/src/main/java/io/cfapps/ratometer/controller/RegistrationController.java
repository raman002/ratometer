package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.model.dto.MemberDTO;
import io.cfapps.ratometer.model.dto.RegisterDTO;
import io.cfapps.ratometer.service.RegistrationService;
import io.cfapps.ratometer.util.web.Response;
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

import static io.cfapps.ratometer.config.MessageSourceConfig.getMessage;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;
    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public ModelAndView register(RedirectView redirectView, @RequestParam Map<String, Object> requestParams) {
        ModelAndView modelAndView = new ModelAndView("register");

        if (redirectView.isRedirectView()) {
            modelAndView.addObject("title", "New Member");
            handleRedirect(modelAndView, requestParams);
        }

        return modelAndView;
    }

    private void handleRedirect(ModelAndView modelAndView, Map<String, Object> requestParams) {
        modelAndView.addAllObjects(requestParams);
    }

    @GetMapping("/success")
    public ModelAndView success() {
        return new ModelAndView("success");
    }

    @PostMapping("/submit")
    public RedirectView loginSubmit(@ModelAttribute("registerDTO") RegisterDTO registerDTO,
                                    RedirectAttributes attributes, HttpSession session) {
        Response<MemberDTO> response = null;

        if (isInvalid(attributes, registerDTO)) {
            return new RedirectView("/register");
        };

        try {
            response = registrationService.registerMember(registerDTO);
        } catch (IOException e) {
            log.error("Something went wrong while connecting to server!", e);
        }

        if (response == null) {
            prepareErrorMessage(attributes, getMessage("usr.err.unknown-error"));
            return new RedirectView("/register");
        }

        int statusCode = response.getCode();

        // If status code is 400 then it means the credentials are rejected.
        if (statusCode == HttpStatus.OK.value()) {
           return new RedirectView("/register/success");
        } else if (statusCode == HttpStatus.BAD_REQUEST.value()){
            log.error("Registration Failed, Something went wrong! ");
            prepareErrorMessage(attributes, response.getMessage());
        }

        return new RedirectView("/register");
    }

    private boolean isInvalid(RedirectAttributes attributes, RegisterDTO registerDTO) {

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            prepareErrorMessage(attributes, getMessage("usr.err.password-dont-match"));
            return true;
        }

        return false;
    }

    private void prepareErrorMessage(RedirectAttributes attributes, String message) {
        attributes.addAttribute("showAlert", true);
        attributes.addAttribute("alertClass", "alert-danger show");
        attributes.addAttribute("message", message);
    }
}
