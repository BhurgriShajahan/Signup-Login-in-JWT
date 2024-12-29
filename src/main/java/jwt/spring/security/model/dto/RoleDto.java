package jwt.spring.security.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jwt.spring.security.enums.RoleType;

public class RoleDto {

    private Long id;
    @NotBlank(message = "First name is required.")
    private RoleType name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }
}
