package MyStore.controllers;

import MyStore.entities.Location;
import MyStore.entities.User;
import MyStore.exceptions.BadRequestException;
import MyStore.payloads.entities.LocationDTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.services.AuthService;
import MyStore.services.LocationService;
import MyStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Location> getUser(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "userId") String orderBy){
        return locationService.getLocations(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Location saveLocation(@RequestBody @Validated LocationDTO body, BindingResult validation) {
        if(validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return locationService.saveLocation(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable UUID locationId) {
        return locationService.getLocationById(locationId);
    }

}
