package net.parvkhandelwal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userPassword;
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email format (e.g., user@example.com)")
    private String email;
}
