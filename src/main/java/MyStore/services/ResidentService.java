package MyStore.services;

import MyStore.entities.MainCategory;
import MyStore.entities.Resident;
import MyStore.entities.SubCategory;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    public Resident getResidentById (long residentId) throws NotFoundException {
        return residentRepository.findById(residentId).orElseThrow(() -> new NotFoundException("Resident",residentId));
    }

    public Resident getResidentByCap(String cap) throws NotFoundException {
        return residentRepository.findByCap(cap).orElseThrow(() -> new NotFoundException("Cap", cap));
    }

    public List<Resident> getResidents() {
        return residentRepository.findAll();
    }
}
