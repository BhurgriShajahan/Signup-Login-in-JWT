package jwt.spring.security.service.impl;

import jwt.spring.security.model.CustomResponseEntity;
import jwt.spring.security.model.dto.RoleDto;
import jwt.spring.security.model.entities.Role;
import jwt.spring.security.model.mapper.RoleMapper;
import jwt.spring.security.repository.RoleRepository;
import jwt.spring.security.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public CustomResponseEntity<RoleDto> createRole(RoleDto roleDto) {
        try {
            if (roleDto == null) {
                logger.error("Role Dto can't be null or empty");
                return CustomResponseEntity.error("Role can't be null or empty");
            } else if (roleDto.getName().equals("null") || roleDto.getName() == null) {
                logger.error("Role can't be null or empty");
                return CustomResponseEntity.error("Role can't be null or empty");
            }
            Role role = this.roleMapper.dtoToEntity(roleDto);
            logger.info("Role Created Successfully...");
            this.roleRepository.save(role);
            return new CustomResponseEntity(role, "Role Created successfully.");
        } catch (Exception exception) {
            logger.info("An error occurred during creating role!!");
            exception.printStackTrace();
            return CustomResponseEntity.error("An error occurred during creating role!!");
        }
    }

    @Override
    public CustomResponseEntity<List<RoleDto>> fetchAllRoles() {
        try {
            List<Role> getAllRoles = this.roleRepository.findAll();
            if (getAllRoles.isEmpty()) {
                logger.error("Roles are empty!!");
                return CustomResponseEntity.error("Roles is empty!");
            }
            List<RoleDto> roles = getAllRoles.stream().map(role -> this.roleMapper.entityToDto(role)).toList();

            return new CustomResponseEntity<>(roles, "Role Fetched successfully.");

        } catch (Exception exception) {
            logger.error("an error occurred during fetch all roles!");
            return CustomResponseEntity.error("an error occurred during fetch all roles!");
        }
    }


}
