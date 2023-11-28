package MyStore.services;

import MyStore.entities.Location;
import MyStore.entities.Municipality;
import MyStore.exceptions.NotFoundException;
import MyStore.payloads.entities.LocationDTO;
import MyStore.repositories.LocationRepository;
import MyStore.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    public Page<Location> getLocations (int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return locationRepository.findAll(pageable);
    }

    public Location getLocationById (UUID locationId) throws NotFoundException {
        return locationRepository.findById(locationId).orElseThrow(() -> new NotFoundException("Location", locationId));
    }

    public Location saveLocation (LocationDTO body) throws IOException, NotFoundException {
        Location location = new Location();
        location.setAddress(body.address());
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.municipalityId() != 0) {}
        Municipality municipality = municipalityRepository.findById(body.municipalityId()).orElseThrow(() -> new NotFoundException("Municipality", body.municipalityId()));
        location.setMunicipality(municipality);
        return locationRepository.save(location);
    }

    public Location updateLocationById (UUID locationId, LocationDTO body) throws NotFoundException {
        Location locationFound = this.getLocationById(locationId);
        locationFound.setAddress(body.address());
//      Nel caso non funzionasse prova a racchiudere le due line sottostanti nel: if (body.municipalityId() != 0) {}
        Municipality municipality = municipalityRepository.findById(body.municipalityId()).orElseThrow(() -> new NotFoundException("Municipality", body.municipalityId()));
        locationFound.setMunicipality(municipality);
        return locationRepository.save(locationFound);
    }

    public void deleteLocationById (UUID locationId) throws NotFoundException{
        locationRepository.deleteById(locationId);
    }
}
