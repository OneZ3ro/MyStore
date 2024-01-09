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

import java.util.*;
import java.util.stream.Collectors;

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

    public Set<Resident> getResidentByFilter (String filterType, String filterValue) {
        if(!filterValue.isBlank()) {
            return switch (filterType.toLowerCase()) {
                case "region" -> new HashSet<>(residentRepository.findAll().stream().filter(resident -> resident.getRegion().equalsIgnoreCase(filterValue)).collect(Collectors.toSet()));
                case "province" -> new HashSet<>(residentRepository.findAll().stream().filter(resident -> resident.getProvince().equalsIgnoreCase(filterValue)).collect(Collectors.toSet()));
                case "acronym" -> new HashSet<>(residentRepository.findAll().stream().filter(resident -> resident.getAcronym().equalsIgnoreCase(filterValue)).collect(Collectors.toSet()));
                case "cap" -> new HashSet<>(residentRepository.findAll().stream().filter(resident -> resident.getCap().equalsIgnoreCase(filterValue)).collect(Collectors.toSet()));
                case "municipality" -> new HashSet<>(residentRepository.findAll().stream().filter(resident -> resident.getMunicipality().equalsIgnoreCase(filterValue)).collect(Collectors.toSet()));
                default -> throw new IllegalStateException("Unexpected value: " + filterType.toLowerCase());
            };
        } else {
            throw new IllegalStateException("Unexpected value: " + filterValue);
        }
    }

    public Set<String> getSetStringByFilter (String filterType) {
            return switch (filterType.toLowerCase()) {
                case "region" -> new TreeSet<>(residentRepository.findAll().stream().map(Resident::getRegion).collect(Collectors.toSet()));
                case "province" -> new TreeSet<>(residentRepository.findAll().stream().map(Resident::getProvince).collect(Collectors.toSet()));
                case "acronym" -> new TreeSet<>(residentRepository.findAll().stream().map(Resident::getAcronym).collect(Collectors.toSet()));
                case "cap" -> new TreeSet<>(residentRepository.findAll().stream().map(Resident::getCap).collect(Collectors.toSet()));
                case "municipality" -> new TreeSet<>(residentRepository.findAll().stream().map(Resident::getMunicipality).collect(Collectors.toSet()));
                default -> new TreeSet<>(List.of("Error"));
            };
    }
}
