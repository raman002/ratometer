package io.cfapps.ratometer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cfapps.ratometer.model.dto.*;
import io.cfapps.ratometer.model.dto.support.CategoryNameComparator;
import io.cfapps.ratometer.model.dto.support.OptionNameComparator;
import io.cfapps.ratometer.model.dto.support.SubCategoryNameComparator;
import io.cfapps.ratometer.service.DashboardService;
import io.cfapps.ratometer.util.web.Response;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
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
            if (CollectionUtils.isEmpty(userDetailsDTO.getRoles())) {
                setRoles(userDetailsDTO);
                httpSession.setAttribute("userDetails", userDetailsDTO);
            }

            try {
                handleRedirect(modelAndView, requestParams, userDetailsDTO, httpSession);
            } catch (IOException e) {
                log.error("Something went wrong while handling dashboard redirect!", e);
            }
        }

        return modelAndView;
    }

    private void setRoles(UserDetailsDTO userDetailsDTO) {
        try {
            Response<List<String>> teamsResponse = dashboardService.loadUserRoles(userDetailsDTO);
            HttpStatus statusCode = HttpStatus.resolve(teamsResponse.getCode());

            switch (statusCode) {
                case OK: {
                    userDetailsDTO.setRoles(teamsResponse.getResult());
                    break;
                }

                case BAD_REQUEST: {
                    log.warn("Roles are not found for user: {}", userDetailsDTO.getUsername());
                    break;
                }

                case INTERNAL_SERVER_ERROR: {
                    log.warn("Something went wrong while fetching roles for user: {}", userDetailsDTO.getUsername());
                    break;
                }

                default:
                    log.error("Unknown status code: {}", statusCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<TeamDTO> prepareTeamsTabData(UserDetailsDTO userDetailsDTO) {
        try {
            Response<List<TeamDTO>> teamsResponse = dashboardService.fetchTeams(userDetailsDTO);
            HttpStatus statusCode = HttpStatus.resolve(teamsResponse.getCode());

            switch (statusCode) {
                case OK: {
                    return teamsResponse.getResult();
                }

                case BAD_REQUEST: {
                    log.warn("Teams are not found for the user: {}", userDetailsDTO.getUsername());
                    break;
                }

                case INTERNAL_SERVER_ERROR: {
                    log.warn("Something went wrong while fetching teams for user: {}", userDetailsDTO.getUsername());
                    break;
                }
                default:
                    log.error("Unknown status code: {}", statusCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }

    private void handleRedirect(ModelAndView modelAndView, Map<String, Object> requestParams,
                                UserDetailsDTO userDetailsDTO, HttpSession httpSession) throws IOException {
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
            } else if (requestParams.containsKey("assign-teams")) {
                assignTeams(modelAndView, userDetailsDTO);
            }
        } else if (roles.contains("USER")) {
            modelAndView.addObject("isUser", true);
            if (requestParams.containsKey("intro")) {
                prepareIntroductionView(modelAndView);
            } else if (requestParams.containsKey("user-rating")) {
                modelAndView.addObject("userRatingTabActive", true);
                modelAndView.addObject("userRatingClass", "active");
                modelAndView.addObject("isRatingAlreadySubmitted", false);

                if (dashboardService.ratingExists(userDetailsDTO).getCode() == HttpStatus.OK.value()) {
                    modelAndView.addObject("isRatingAlreadySubmitted", true);
                    return;
                }
                setUserCategories(modelAndView, httpSession);
            }
        }
    }

    private void assignTeams(ModelAndView modelAndView, UserDetailsDTO userDetailsDTO) {
        modelAndView.addObject("teamAssignmentTabActive", true);
        modelAndView.addObject("teamAssignmentTabClass", "active");
        modelAndView.addObject("unassignedMembers", loadUnassignedMembers(userDetailsDTO));
        modelAndView.addObject("teams", prepareTeamsTabData(userDetailsDTO));
    }

    private List<MemberDTO> loadUnassignedMembers(UserDetailsDTO userDetailsDTO) {
        try {
            Response<List<MemberDTO>> response = dashboardService.fetchMembers(userDetailsDTO, true);

            HttpStatus statusCode = HttpStatus.resolve(response.getCode());

            switch (statusCode) {
                case OK: {
                    return response.getResult();
                }

                case BAD_REQUEST: {
                    log.warn("Categories are not for user: {}", userDetailsDTO.getUsername());
                    break;
                }
                case INTERNAL_SERVER_ERROR: {
                    log.warn("Something went wrong while fetching categories for user: {}", userDetailsDTO
                            .getUsername());
                    break;
                }
                default: {
                    log.error("Unknown status code: {}", statusCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    private void prepareIntroductionView(ModelAndView modelAndView) {
        modelAndView.addObject("introTabActive", true);
        modelAndView.addObject("introTabClass", "active");
    }


    private void setUserCategories(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("categories", fetchCategories(session));
    }

    private List<CategoriesDTO> splitCategories(List<CategoriesDTO> categoriesDTOS) {

        List<CategoriesDTO> categories = categoriesDTOS.stream().filter(e -> "CATEGORY".equals(e.getCategoriesType()))
                .collect(Collectors.toList());

        // Remove the extracted categories.
        categoriesDTOS.removeAll(categories);

        for (CategoriesDTO category : categories) {

            List<CategoriesDTO> subCategories = categoriesDTOS.stream()
                    .filter(e -> "SUB_CATEGORY".equals(e.getCategoriesType()) && e.getParentCategoryId() == category.getPk())
                    .collect(Collectors.toList());

            // Map the subcategories with categories.
            for(CategoriesDTO cat: subCategories) {
                SubCategoriesDTO subCategoriesDTO = new SubCategoriesDTO();
                subCategoriesDTO.setPk(cat.getPk());
                subCategoriesDTO.setUuid(cat.getUuid());
                subCategoriesDTO.setName(cat.getName());
                category.getSubCategories().add(subCategoriesDTO);
            };

            // Remove the extracted sub categories.
            categoriesDTOS.removeAll(subCategories);
        }

        // Iteration for extracting options.
        for (CategoriesDTO category : categories) {
            List<SubCategoriesDTO> subCategories = category.getSubCategories();

            for (SubCategoriesDTO subCategory : subCategories) {
                List<CategoriesDTO> options = categoriesDTOS.stream()
                        .filter(e -> "OPTION".equals(e.getCategoriesType()) && e.getParentCategoryId() == subCategory.getPk())
                        .collect(Collectors.toList());

                // Map the options with sub categories.
                for(CategoriesDTO cat: options) {
                    OptionsDTO option = new OptionsDTO();
                    option.setUuid(cat.getUuid());
                    option.setName(cat.getName());
                    option.setOptionOrderId(cat.getOptionOrderId());
                    subCategory.getOptions().add(option);
                };

                Collections.sort(subCategory.getOptions(), new OptionNameComparator());
                // Remove the extracted sub categories.
                categoriesDTOS.removeAll(options);
            }

            Collections.sort(subCategories, new SubCategoryNameComparator());
        }

        Collections.sort(categories, new CategoryNameComparator());

        return categories;
    }

    private List<CategoriesDTO> fetchCategories(HttpSession session) {
        List<CategoriesDTO> existingCategories = (List<CategoriesDTO>) session.getAttribute("categories");
        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) session.getAttribute("userDetails");

        if (existingCategories == null) {
            try {
                Response<List<CategoriesDTO>> httpResponse = dashboardService.fetchCategories(userDetailsDTO);
                HttpStatus statusCode = HttpStatus.resolve(httpResponse.getCode());

                switch (statusCode) {
                    case OK: {
                        List<CategoriesDTO> categories = splitCategories(httpResponse.getResult());
                        session.setAttribute("categories", categories);
                        return categories;
                    }

                    case BAD_REQUEST: {
                        log.warn("Categories are not for user: {}", userDetailsDTO.getUsername());
                        break;
                    }
                    case INTERNAL_SERVER_ERROR: {
                        log.warn("Something went wrong while fetching categories for user: {}", userDetailsDTO
                                .getUsername());
                        break;
                    }
                    default: {
                        log.error("Unknown status code: {}", statusCode);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return existingCategories;
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
            Response<List<MemberDTO>> response = dashboardService.fetchMembers(userDetailsDTO, false);
            HttpStatus statusCode = HttpStatus.resolve(response.getCode());

            switch (statusCode) {
                case OK: {
                    return response.getResult();
                }

                case BAD_REQUEST: {
                    log.warn("Members are not found for user: {}", userDetailsDTO.getUsername());
                    break;
                }

                case INTERNAL_SERVER_ERROR: {
                    log.warn("Something went wrong while fetching members for user: {}", userDetailsDTO.getUsername());
                    break;
                }

                default:
                    log.error("Unknown response code!");
                    return Collections.EMPTY_LIST;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return Collections.EMPTY_LIST;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping(path = "/assign-teams", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response<String>> assignTeams(@RequestParam MultiValueMap multiValueMap, HttpSession session) {

        List data = (List) multiValueMap.get("data");

        try {
            dashboardService.assignTeams((String) data.get(0), (UserDetailsDTO) session.getAttribute("userDetails"));
            return ResponseEntity.ok(Response.ok("Team(s) assigned successfully!"));
        } catch (Exception e) {
            log.error("Could not map the assign team data!", e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while assigning teams!"));
    }

    @PostMapping(path="/submit-user-ratings", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Response<String>> submitUserRatings(@RequestParam MultiValueMap multiValueMap, HttpSession session) {

        List data = (List) multiValueMap.get("data");

        try {
            dashboardService.submitUserRatings((String) data.get(0), (UserDetailsDTO) session.getAttribute("userDetails"));
            return ResponseEntity.ok(Response.ok("Rating(s) submitted successfully!"));
        } catch (Exception e) {
            log.error("Could not submit the ratings!", e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Response.of(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while submitting ratings!"));
    }
}
