package jwt.spring.security.model.mapper;

import jwt.spring.security.model.dto.RoleDto;
import jwt.spring.security.model.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto entityToDto(Role role);

    Role dtoToEntity(RoleDto roleDto);

}
