package jwt.spring.security.model.mapper;

import jwt.spring.security.model.dto.UserSignupDto;
import jwt.spring.security.model.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignupMapper {

    UserSignupDto entityToDto(User user);

    User dtoToEntity(UserSignupDto userSignupDto);
}
