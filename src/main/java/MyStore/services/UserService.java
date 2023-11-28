package MyStore.services;

import MyStore.entities.Location;
import MyStore.entities.User;
import MyStore.exceptions.NotFoundException;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationService locationService;

    public Page<User> getUsers (int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public User getUserById (UUID userId) throws NotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
    }

    public User updateUserById (UUID userId, UserRegistrationDTO body) throws NotFoundException {
        User userFound = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User", userId));
        userFound.setName(body.name());
        userFound.setSurname(body.surname());
        userFound.setUsername(body.username());
        userFound.setEmail(body.email());
//      userFound.setPassword(bcrypt.encode(body.password()));     da sistemare la reimpostazione della password
        userFound.setBorn(body.born());
        userFound.setImgProfile(body.urlImgProfile());
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.locationId() != null) {}
        Location location = locationService.getLocationById(body.locationId());
        userFound.setLocation(location);

        return userRepository.save(userFound);
    }

    public void deleteUserById (UUID userId) throws NotFoundException{
        userRepository.deleteById(userId);
    }
}
