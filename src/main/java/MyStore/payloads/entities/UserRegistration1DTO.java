package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRegistration1DTO(
        @NotEmpty(message = "The username field is mandatory")
        @Size(min = 2, max = 16, message = "The username must be min 2 and max 16 length")
        String username,
        @NotEmpty(message = "The email field is mandatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email entered is invalid")
        String email,
        @NotEmpty(message = "The password field is mandatory")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}$", message = """
                The password must:
                - Be min 8 and max 32 length
                - Contains at least one digit
                - Contains at least one lower alpha char
                - Contains at least one upper alpha char
                - Contains at least one char within a set of special chars (@#%$^ etc.)
                - Does not contain space, tab, etc...
                """)
        String password
) {}
