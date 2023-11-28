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

    public void deleteUserById (UUID userId) throws NotFoundException{
        userRepository.deleteById(userId);
    }

    public User getUserByEmail (String email) throws NotFoundException{
        return userRepository.findByEmail(email).orElseThrow(() ->  new NotFoundException(email));
    }
}
