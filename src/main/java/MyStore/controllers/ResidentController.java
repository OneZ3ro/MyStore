package MyStore.controllers;

import MyStore.entities.Resident;
import MyStore.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/places")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @GetMapping("")
    public Page<Resident> getResidents(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "12") int size,
                                     @RequestParam(defaultValue = "residentId") String orderBy){
        return residentService.getResidentsPage(page, size, orderBy);
    }
    @GetMapping("/filterBy")
    public Set<Resident> getResidentByFilter(@RequestParam("type") String filterType, @RequestParam("value") String filterValue) {
        return residentService.getResidentByFilter(filterType, filterValue);
    }

    @GetMapping("/residentsString")
    public Set<String> getSetStringByFilter(@RequestParam("type") String filterType) {
        return residentService.getSetStringByFilter(filterType);
    }

}
