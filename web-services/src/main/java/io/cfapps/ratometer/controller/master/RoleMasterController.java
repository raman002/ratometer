package io.cfapps.ratometer.controller.master;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.master.RoleMaster;
import io.cfapps.ratometer.service.master.RoleMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

import static io.cfapps.ratometer.config.MessageSourceConfig.getMessage;

@RestController
@RequestMapping("/roles")
public class RoleMasterController implements ValidationErrorProcessor {

    private final RoleMasterService roleMasterService;

    public RoleMasterController(RoleMasterService roleMasterService) {
        this.roleMasterService = roleMasterService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response<List<RoleMaster>>> getAllRoles() {
        return ResponseEntity.ok(Response.ok(roleMasterService.findAllByIsDeleted()));
    }

    @GetMapping("/get-default")
    public ResponseEntity<Response<RoleMaster>> getDefaultRole() {
        return ResponseEntity.ok(Response.ok(roleMasterService.getDefaultRole()));
    }

    @PostMapping("/create-role")
    public ResponseEntity<Response> createRole(@RequestBody @Valid RoleMaster roleMaster, BindingResult bindingResult) {
        String roleName = StringUtils.defaultString(roleMaster.getRoleName());

        if (bindingResult.hasErrors()) {
            return processValidationErrors(bindingResult);
        }

        if (roleName.isEmpty()) {
            throw new ValidationException(getMessage("role.err.rolename-required"));
        }

        return ResponseEntity.ok(Response.ok(roleMasterService.save(roleMaster)));
    }
}
