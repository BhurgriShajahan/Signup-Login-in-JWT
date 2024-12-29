package jwt.spring.security.controller;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.RoleDto;
import org.springframework.web.bind.annotation.*;

import jwt.spring.security.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/v1/role/")
public class RoleController {

    RoleService roleService;

    RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Create Role
    @PostMapping
    public CustomResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        return this.roleService.createRole(roleDto);
    }

    //Fetch all roles
    @GetMapping
    public CustomResponseEntity<List<RoleDto>> fetchAllRoles() {
        return this.roleService.fetchAllRoles();
    }

}
