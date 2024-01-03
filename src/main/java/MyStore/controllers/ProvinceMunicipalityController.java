package MyStore.controllers;

import MyStore.services.ProvinceMunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/places")
public class ProvinceMunicipalityController {
    @Autowired
    private ProvinceMunicipalityService provinceMunicipalityService;

    @GetMapping("/region")
    public Set<String> getRegions() {
        return provinceMunicipalityService.getRegions();
    }

    @GetMapping("/provinces")
    public Set<String> getProvinces(@RequestParam("region") String region) {
        return provinceMunicipalityService.getProvincesByRegion(region);
    }

    @GetMapping("/sigla")
    public String getSigla(@RequestParam("provinceName") String provinceName) {
        return provinceMunicipalityService.getSiglaByProvinceName(provinceName);
    }

    @GetMapping("/caps")
    public Set<String> getCaps(@RequestParam("sigla") String sigla) {
        return provinceMunicipalityService.getCapsBySigla(sigla);
    }

    @GetMapping("/municipalities")
    public Set<String> getMunicipalities(@RequestParam("cap") String cap) {
        return provinceMunicipalityService.getMunicipalitiesByCap(cap);
    }
}
