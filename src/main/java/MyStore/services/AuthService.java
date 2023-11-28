package MyStore.services;

import MyStore.entities.Location;
import MyStore.entities.User;
import MyStore.enums.Role;
import MyStore.exceptions.BadRequestException;
import MyStore.exceptions.NotFoundException;
import MyStore.exceptions.UnauthorizedException;
import MyStore.payloads.entities.UserLoginDTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.repositories.UserRepository;
import MyStore.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) throws Exception{
        User user = userService.getUserByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public User registerUser(UserRegistrationDTO body) throws IOException {
        userRepository.findByEmail(body.email()).ifPresent( user -> {
            throw new BadRequestException("Email " + user.getEmail() + " has already been used. Please try with another email");
        });
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setBorn(body.born());
        newUser.setImgProfile(body.urlImgProfile());
        newUser.setRoles(Arrays.asList(Role.USER));
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.locationId() != null) {}
        Location location = locationService.getLocationById(body.locationId());
        newUser.setLocation(location);
        return userRepository.save(newUser);
    }

    public User updateUserById (UUID userId, UserRegistrationDTO body) throws NotFoundException {
        User userFound = userService.getUserById(userId);
        userFound.setName(body.name());
        userFound.setSurname(body.surname());
        userFound.setUsername(body.username());
        userFound.setEmail(body.email());
        userFound.setPassword(bcrypt.encode(body.password()));
        userFound.setBorn(body.born());
        userFound.setImgProfile(body.urlImgProfile());
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.locationId() != null) {}
        Location location = locationService.getLocationById(body.locationId());
        userFound.setLocation(location);
        return userRepository.save(userFound);
    }
}
