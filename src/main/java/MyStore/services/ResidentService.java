package MyStore.services;

import MyStore.entities.MainCategory;
import MyStore.entities.Product;
import MyStore.entities.Resident;
import MyStore.entities.SubCategory;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Resident> getResidentsPage (int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return residentRepository.findAll(pageable);
    }
}
