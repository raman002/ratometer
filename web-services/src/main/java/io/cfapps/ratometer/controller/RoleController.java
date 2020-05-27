package io.cfapps.ratometer.controller;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.service.RoleService;
import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-roles")
public class RoleController implements ValidationErrorProcessor {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<Response<List<String>>> getAllRoles(@RequestHeader("username") String username) {
        return ResponseEntity.ok(Response.ok(roleService.getRoleNamesByUsername(username)));
    }
}
