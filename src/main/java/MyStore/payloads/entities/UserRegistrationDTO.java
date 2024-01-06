package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record UserRegistrationDTO(
//        Fields: name, surname, username, email, password, born, urlImgProfile, locationId
        @NotEmpty(message = "The name field is mandatory")
        @Size(min = 3, max = 20, message = "The name must be min 3 and max 20 length")
        String name,
        @NotEmpty(message = "The surname field is mandatory")
        @Size(min = 3, max = 30, message = "The surname must be min 3 and max 30 length")
        String surname,
        @NotEmpty(message = "The username field is mandatory")
        @Size(min = 2, max = 16, message = "The username must be min 2 and max 16 length")
        String username,
        @NotEmpty(message = "The email field is mandatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email entered is invalid")
        String email,
//        @NotEmpty(message = "The password field is mandatory")
//        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_+~]).{8,32}$", message = """
//                The password must:
//                - Be min 8 and max 32 length
//                - Contains at least one digit
//                - Contains at least one lower alpha char
//                - Contains at least one upper alpha char
//                - Contains at least one char within a set of special chars (!@#$%^&*_+~)
//                - Does not contain space, tab, etc...
//                """)
        String oldPassword,
        String newPassword,
        LocalDate born,
        @NotEmpty(message = "The address filed is mandatory")
        String address,
        @NotEmpty(message = "The municipality name is mandatory")
        String municipalityName,
        String urlImgProfile
) {}
