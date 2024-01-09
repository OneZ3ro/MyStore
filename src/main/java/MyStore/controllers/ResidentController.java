package MyStore.controllers;

import MyStore.entities.Resident;
import MyStore.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
