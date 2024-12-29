package jwt.spring.security.model.mapper;

import jwt.spring.security.model.dto.ContactDto;
import jwt.spring.security.model.entities.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact dtoToEntity(ContactDto contactDto);

    @Mapping(target = "userId", source = "user.id")
    ContactDto entityToDto(Contact contact);
}
