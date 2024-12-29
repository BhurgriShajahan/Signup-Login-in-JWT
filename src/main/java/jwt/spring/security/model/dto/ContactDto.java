package jwt.spring.security.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;
public class ContactDto {

    private Long id;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be valid and contain 10 to 15 digits.")
    private String phoneNo;

    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters.")
    private String lastName;

    @Size(max = 250, message = "About section must not exceed 250 characters.")
    private String about;

    @PastOrPresent(message = "Created date cannot be in the future.")
    private Date createdAt;

    @PastOrPresent(message = "Updated date cannot be in the future.")
    private Date updatedAt;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Phone number is required.") @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be valid and contain 10 to 15 digits.") String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(@NotBlank(message = "Phone number is required.") @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Phone number must be valid and contain 10 to 15 digits.") String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public @NotBlank(message = "First name is required.") @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters.") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First name is required.") @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters.") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "Last name is required.") @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters.") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "Last name is required.") @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters.") String lastName) {
        this.lastName = lastName;
    }

    public @Size(max = 250, message = "About section must not exceed 250 characters.") String getAbout() {
        return about;
    }

    public void setAbout(@Size(max = 250, message = "About section must not exceed 250 characters.") String about) {
        this.about = about;
    }

    public @PastOrPresent(message = "Created date cannot be in the future.") Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@PastOrPresent(message = "Created date cannot be in the future.") Date createdAt) {
        this.createdAt = createdAt;
    }


    public @PastOrPresent(message = "Updated date cannot be in the future.") Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@PastOrPresent(message = "Updated date cannot be in the future.") Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
