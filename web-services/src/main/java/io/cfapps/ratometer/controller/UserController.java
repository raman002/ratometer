package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.annotations.web.Create;
import io.cfapps.ratometer.annotations.web.Update;
import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.User;
import io.cfapps.ratometer.model.dto.UserDTO;
import io.cfapps.ratometer.service.RoleService;
import io.cfapps.ratometer.service.TeamService;
import io.cfapps.ratometer.service.UserService;
import io.cfapps.ratometer.util.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.cfapps.ratometer.config.MessageSourceConfig.getMessage;

@RestController
@RequestMapping("/users")
public class UserController implements ValidationErrorProcessor {

    private Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final TeamService teamService;

    public UserController(UserService userService, TeamService teamService) {
        this.userService = userService;
        this.teamService = teamService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<Response> createUser(@Validated(Create.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return processValidationErrors(bindingResult);
        }

        userService.validateNewUser(userDTO);

        User user = new User();
        userService.copyProperties(userDTO, user);
        userService.save(user);

        // Return the generated uuid once the record is saved.
        userDTO.setUuid(user.getUuid());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.ok(getMessage("usr.ok.account-created"), userDTO));
    }

    @PutMapping("/update-user")
    public ResponseEntity<Response> updateUser(@Validated(Update.class) @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return processValidationErrors(bindingResult);
        }

        UUID uuid = userDTO.getUuid();
        User user = userService.findByUuid(uuid);
        userService.validateExistingUser(user, uuid.toString());
        userService.copyProperties(userDTO, user);
        userService.update(user);

        return ResponseEntity.ok(Response.ok(getMessage("usr.ok.account-updated"), userDTO));
    }

    @GetMapping("/get-all-team-members")
    public ResponseEntity<Response<List<UserDTO>>> getAllTeamMembers(@RequestHeader("username") String username) {
        List<User> userList = userService.findAllByIsDeleted();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            if (user.getUsername().equals(username)) continue;

            UserDTO userDTO = new UserDTO();
            userService.copyProperties(user, userDTO);

            // Add roles for a particular user.
            userDTO.setTeams(teamService.fetchTeamsByUsername(user.getUsername()).stream().collect(Collectors.toSet()));
            userDTOList.add(userDTO);
        }

        return ResponseEntity.ok(Response.ok(userDTOList));
    }

    @GetMapping("/get-unassigned-members")
    public ResponseEntity<Response<List<UserDTO>>> getUnassignedTeamMembers(@RequestHeader("username") String username) {
        List<User> userList = userService.findAllByIsDeleted();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            if (user.getUsername().equals(username)) continue;

            if (CollectionUtils.isEmpty(teamService.fetchTeamsByUsername(user.getUsername()))) {
                UserDTO userDTO = new UserDTO();
                userService.copyProperties(user, userDTO);
                userDTOList.add(userDTO);
            }
        }

        return ResponseEntity.ok(Response.ok(userDTOList));
    }
}
