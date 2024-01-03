package MyStore.services;

import MyStore.entities.Municipality;
import MyStore.entities.Province;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.MunicipalityRepository;
import MyStore.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ProvinceMunicipalityService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    public Set<String> getRegions() {
        return new TreeSet<>(provinceRepository.findAll().stream().map(province -> province.getRegion()).collect(Collectors.toSet()));
    }

    public Set<String> getProvincesByRegion(String region) {
        return new TreeSet<>(provinceRepository.findAll().stream().filter(province -> province.getRegion().toLowerCase().equals(region.toLowerCase())).map(province -> province.getName()).collect(Collectors.toSet()));
    }

    public String getSiglaByProvinceName(String provinceName) throws NotFoundException {
        return provinceRepository.findByName(provinceName).orElseThrow(() -> new NotFoundException("provice name")).getSigla();
    }

    public Set<String> getCapsBySigla(String sigla) throws NotFoundException {
        Set<String> caps = new TreeSet<>();
        String fileMunicipalities = "src/main/java/MyStore/myfiles/listacomuni.csv";
        BufferedReader readerMunicipalities = null;
        String lineMunicipality = "";
        long counterMunicipality = 0;
        try {
            readerMunicipalities = new BufferedReader(new FileReader(fileMunicipalities));
            while ((lineMunicipality = readerMunicipalities.readLine()) != null) {
                if (counterMunicipality >= 1) {
                    String[] row = lineMunicipality.split(";");
                    if (row[2].toLowerCase().equals(sigla.toLowerCase())) {
                        caps.add(row[5]);
                    }
                }
                counterMunicipality++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                readerMunicipalities.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return caps;
    }

    public Set<String> getMunicipalitiesByCap(String cap) throws NotFoundException {
        return new TreeSet<>(municipalityRepository.findByCap(cap).orElseThrow(() -> new NotFoundException("cap")).stream().map(municipality -> municipality.getName()).collect(Collectors.toSet()));
    }
}
