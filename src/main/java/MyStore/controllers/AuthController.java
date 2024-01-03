package MyStore.controllers;

import MyStore.entities.User;
import MyStore.exceptions.BadRequestException;
import MyStore.payloads.entities.UserLoginDTO;
import MyStore.payloads.entities.UserLoginSuccessDTO;
import MyStore.payloads.entities.UserRegistration1DTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody @Validated UserRegistration1DTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.registerUser(body);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @PostMapping("/login")
    public UserLoginSuccessDTO loginUser(@RequestBody @Validated UserLoginDTO body) throws Exception {
        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }
}
