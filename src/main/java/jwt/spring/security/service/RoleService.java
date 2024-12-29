package jwt.spring.security.service;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.RoleDto;

import java.util.List;

public interface RoleService {

    //Create Role
    CustomResponseEntity<RoleDto> createRole(RoleDto roleDto);

    //Fetch All Roles
    CustomResponseEntity<List<RoleDto>> fetchAllRoles();
}
