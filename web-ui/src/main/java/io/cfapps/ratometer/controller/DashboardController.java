package io.cfapps.ratometer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.model.dto.*;
import io.cfapps.ratometer.service.DashboardService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private final DashboardService dashboardService;
    private final ObjectMapper objectMapper;

    public DashboardController(DashboardService dashboardService, ObjectMapper objectMapper) {
        this.dashboardService = dashboardService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public Object dashboard(HttpSession httpSession, @RequestParam Map<String, Object> requestParams,
                            RedirectView redirectView) {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) httpSession.getAttribute("userDetails");

        if (userDetailsDTO == null) {
            return new RedirectView("/login");
        }

        if (redirectView.isRedirectView()) {
            setRoles(userDetailsDTO);
            handleRedirect(modelAndView, requestParams, userDetailsDTO, httpSession);
        }

        return modelAndView;
    }

    private void setRoles(UserDetailsDTO userDetailsDTO) {
        try {
            HttpResponse<String> userRolesResponse = dashboardService.loadUserRoles(userDetailsDTO);
            if (userRolesResponse != null) {
                HttpStatus statusCode = HttpStatus.resolve(userRolesResponse.statusCode());

                switch (statusCode) {
                    case OK -> {
                        Response<List<String>> response = objectMapper.readValue(userRolesResponse.body(), new TypeReference<>() {});
                        userDetailsDTO.setRoles(response.getResult());
                    }

                    case BAD_REQUEST ->
                        log.warn("Roles are not found for user: {}", userDetailsDTO.getUsername());

                    case INTERNAL_SERVER_ERROR ->
                        log.warn("Something went wrong while fetching roles for user: {}", userDetailsDTO.getUsername());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<TeamDTO> prepareTeamsTabData(UserDetailsDTO userDetailsDTO) {
        try {
            HttpResponse<String> teamsResponse = dashboardService.fetchTeams(userDetailsDTO);
            HttpStatus statusCode = HttpStatus.resolve(teamsResponse.statusCode());

            return switch (statusCode) {
                case OK -> {
                    Response<List<TeamDTO>> response = objectMapper.readValue(teamsResponse.body(), new TypeReference<>() {});
                    yield response.getResult();
                }

                case BAD_REQUEST -> {
                    log.warn("Roles are not found for user: {}", userDetailsDTO.getUsername());
                    yield List.of();
                }

                case INTERNAL_SERVER_ERROR -> {
                    log.warn("Something went wrong while fetching roles for user: {}", userDetailsDTO.getUsername());
                    yield List.of();
                }

                default -> List.of();
            };
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return List.of();
    }

    private void handleRedirect(ModelAndView modelAndView, Map<String, Object> requestParams,
                                UserDetailsDTO userDetailsDTO, HttpSession httpSession) {
        modelAndView.addObject("title", "Dashboard");
        modelAndView.addObject("showLogout", true);
        modelAndView.addAllObjects(requestParams);
        List<String> roles = userDetailsDTO.getRoles();

        if (roles.contains("SUPER_ADMIN")) {
            modelAndView.addObject("isSuperAdmin", true);

            if (requestParams.containsKey("members")) {
                setMembersProperties(modelAndView, userDetailsDTO);
            } else if (requestParams.containsKey("teams")) {
                setTeamsProperties(modelAndView, userDetailsDTO);
            } else if (requestParams.containsKey("admin-rating")) {
                setAdminRatingProperties(modelAndView, userDetailsDTO);
            }
        } else if (roles.contains("USER")) {
            setUserCategories(modelAndView, httpSession);
        }
    }

    private void setUserCategories(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("userRatingTabActive", true);
        modelAndView.addObject("userRatingClass", "active");
        modelAndView.addObject("isUser", true);
        modelAndView.addObject("categories", fetchCategories(session));
    }

    private List<CategoriesDTO> fetchCategories(HttpSession session) {
        List<CategoriesDTO> categories = (List<CategoriesDTO>) session.getAttribute("categories");
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) session.getAttribute("userDetails");

        if (categories == null) {
            try {
                HttpResponse<String> httpResponse = dashboardService
                        .fetchCategories(userDetailsDTO);

                HttpStatus statusCode = HttpStatus.resolve(httpResponse.statusCode());

                switch (statusCode) {
                    case OK -> {
                        Response<List<CategoriesDTO>> response = objectMapper.readValue(httpResponse.body(), new TypeReference<>() {});
                        session.setAttribute("categories", response.getResult());
                    }

                    case BAD_REQUEST ->
                            log.warn("Roles are not found for user: {}", userDetailsDTO.getUsername());

                    case INTERNAL_SERVER_ERROR ->
                            log.warn("Something went wrong while fetching roles for user: {}", userDetailsDTO.getUsername());
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return categories;
    }

    private Map<String, List<CategoriesDTO>> transformCategories(Response<List<CategoriesDTO>> response) {
        List<CategoriesDTO> result = response.getResult();

        List<CategoriesDTO> categories = result.stream().filter(e -> e.getCategoriesType().equals("CATEGORY"))
                .collect(Collectors.toList());

        List<CategoriesDTO> subCategories = result.stream().filter(e -> e.getCategoriesType().equals("SUB_CATEGORY"))
                .collect(Collectors.toList());

        List<CategoriesDTO> options = result.stream().filter(e -> e.getCategoriesType().equals("OPTION"))
                .collect(Collectors.toList());

        int categorySize = categories.size();
        int subcategorySize = subCategories.size();
        int optionsSize = options.size();

        Map<String, List<SubCategoriesDTO>> categoriesMap = categories.get(0).getCategoriesMap();

        for (int count = 0; count < categorySize; categorySize++) {

            for (int countValue = 0; countValue < subcategorySize; subcategorySize++) {

                for (int optionValue = 0; optionValue < optionsSize; optionsSize++) {

                }
            }
        }

        return null;
    }

    private void setAdminRatingProperties(ModelAndView modelAndView, UserDetailsDTO userDetailsDTO) {
        modelAndView.addObject("adminRatingClass", "active");
        modelAndView.addObject("adminRatingTabActive", true);
    }

    private void setTeamsProperties(ModelAndView modelAndView, UserDetailsDTO userDetailsDTO) {
        modelAndView.addObject("teamsTabActive", true);
        modelAndView.addObject("teamsTabClass", "active");
        modelAndView.addObject("teams", prepareTeamsTabData(userDetailsDTO));
    }

    private void setMembersProperties(ModelAndView modelAndView, UserDetailsDTO userDetailsDTO) {
        modelAndView.addObject("membersTabActive", true);
        modelAndView.addObject("membersTabClass", "active");
        modelAndView.addObject("members", loadMembers(userDetailsDTO));
    }

    public List<MemberDTO> loadMembers(UserDetailsDTO userDetailsDTO) {
        try {
            HttpResponse<String> teamsResponse = dashboardService.fetchMembers(userDetailsDTO);
            HttpStatus statusCode = HttpStatus.resolve(teamsResponse.statusCode());

            return switch (statusCode) {
                case OK -> {
                    Response<List<MemberDTO>> response = objectMapper.readValue(teamsResponse.body(), new TypeReference<>() {});
                    yield response.getResult();
                }

                case BAD_REQUEST -> {
                    log.warn("Roles are not found for user: {}", userDetailsDTO.getUsername());
                    yield List.of();
                }

                case INTERNAL_SERVER_ERROR -> {
                    log.warn("Something went wrong while fetching roles for user: {}", userDetailsDTO.getUsername());
                    yield List.of();
                }
                default -> List.of();

            };
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return List.of();
    }
}
