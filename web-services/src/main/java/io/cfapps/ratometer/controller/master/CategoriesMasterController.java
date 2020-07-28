package io.cfapps.ratometer.controller.master;

import io.cfapps.ratometer.controller.support.ValidationErrorProcessor;
import io.cfapps.ratometer.entity.master.CategoriesMaster;
import io.cfapps.ratometer.entity.master.RoleMaster;
import io.cfapps.ratometer.service.master.CategoriesMasterService;
import io.cfapps.ratometer.service.master.RoleMasterService;
import io.cfapps.ratometer.util.web.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesMasterController implements ValidationErrorProcessor {

    private final CategoriesMasterService service;

    public CategoriesMasterController(CategoriesMasterService service) {
        this.service = service;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response<List<CategoriesMaster>>> getAllCategories() {
        return ResponseEntity.ok(Response.ok(service.findAllByIsDeleted()));
    }
}
