package MyStore.services;

import MyStore.entities.Municipality;
import MyStore.entities.Province;
import MyStore.entities.Resident;
import MyStore.entities.User;
import MyStore.enums.Role;
import MyStore.exceptions.BadRequestException;
import MyStore.exceptions.NotFoundException;
import MyStore.exceptions.UnauthorizedException;
import MyStore.payloads.entities.UserDTO;
import MyStore.payloads.entities.UserLoginDTO;
import MyStore.payloads.entities.UserRegistration1DTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.repositories.MunicipalityRepository;
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
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private ResidentService residentService;

    public String authenticateUser(UserLoginDTO body) throws Exception{
        User user = userService.getUserByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    public User registerUser(UserRegistration1DTO body) throws IOException {
        userRepository.findByEmail(body.email()).ifPresent( user -> {
            throw new BadRequestException("Email " + user.getEmail() + " has already been used. Please try with another email");
        });
        User newUser = new User();
//        newUser.setName(body.name());
//        newUser.setSurname(body.surname());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setPassword(bcrypt.encode(body.password()));
//        newUser.setBorn(body.born());
//        newUser.setAddress(body.address());
////      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.municipalityId() != 0) {}
//        Municipality municipality = municipalityRepository.findById(body.municipalityId()).orElseThrow(() -> new NotFoundException("Municipality", body.municipalityId()));
//        newUser.setMunicipality(municipality);
//        newUser.setImgProfile(body.urlImgProfile());
        newUser.setRoles(Arrays.asList(Role.USER));
        return userRepository.save(newUser);
    }

    public UserDTO updateUserById (UUID userId, UserRegistrationDTO body) throws NotFoundException {
        User userFound = userService.getUserById(userId);
            userFound.setName(body.name());
            userFound.setSurname(body.surname());
            userFound.setUsername(body.username());
            userFound.setEmail(body.email());
//        userFound.setPassword(bcrypt.encode(body.password()));
            userFound.setBorn(body.born());
            userFound.setAddress(body.address());
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.municipalityId() != 0) {}
//            Municipality municipality = municipalityRepository.findByName(body.municipalityName()).orElseThrow(() -> new NotFoundException("Municipality", body.municipalityName()));
//        Province province = municipalityRepository.findById(municipality.getMunicipalityId()).orElseThrow(() -> new NotFoundException("municipalityId")).getProvince();
//            userFound.setMunicipality(municipality);
        Resident resident = residentService.getResidentByCap(body.cap());
        userFound.setResident(resident);
            userFound.setImgProfile(body.urlImgProfile());

        if(body.newPassword() == null) {
            userRepository.save(userFound);
        } else {
            if (bcrypt.matches(body.oldPassword(), userFound.getPassword())) {
                userFound.setPassword(bcrypt.encode(body.newPassword()));
                userRepository.save(userFound);
            } else {
                throw new UnauthorizedException("Invalid credentials");
            }
        }
        return new UserDTO(body.name(), body.surname(), body.born(),resident, body.address().split(", ")[0], Long.parseLong(body.address().split(", ")[1]), body.username(), body.email());
    }
}
