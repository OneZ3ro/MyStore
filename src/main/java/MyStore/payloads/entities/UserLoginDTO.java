package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(
        @NotEmpty(message = "The email field is mandatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email entered is invalid")
        String email,
        @NotEmpty(message = "The password field is mandatory")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$", message = """
                The password must:
                - Be min 8 and max 16 length
                - Contains at least one digit
                - Contains at least one lower alpha char
                - Contains at least one upper alpha char
                - Contains at least one char within a set of special chars (@#%$^ etc.)
                - Does not contain space, tab, etc...
                """)
        String password
) {
}
