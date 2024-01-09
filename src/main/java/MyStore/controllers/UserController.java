package MyStore.controllers;

import MyStore.entities.User;
import MyStore.payloads.entities.UserDTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.services.AuthService;
import MyStore.services.OrderService;
import MyStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "12") int size,
                              @RequestParam(defaultValue = "userId") String orderBy){
        return userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/me")
    public UserDetails getProfileUser(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }

    @PutMapping("/me")
    public User updateProfileUser(@AuthenticationPrincipal User currentUser, @RequestBody @Validated UserRegistrationDTO body) {
        return authService.updateUserById(currentUser.getUserId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfileUser(@AuthenticationPrincipal User currentUser) {
        userService.deleteUserById(currentUser.getUserId());
    }

    @PostMapping("/me/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImg(@AuthenticationPrincipal User currentUser, @RequestParam("imgProfile") MultipartFile body) throws IOException {
        return userService.uploadImgProfile(currentUser.getUserId(), body);
    }


}
