package MyStore.runners;

import MyStore.entities.Municipality;
import MyStore.entities.Province;
import MyStore.exceptions.NotFoundException;
import MyStore.repositories.MunicipalityRepository;
import MyStore.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(1)
public class AddProvincesAndMunicipalitiesRunner implements CommandLineRunner {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public void run(String... args) throws Exception {
        if (provinceRepository.findAll().isEmpty() && municipalityRepository.findAll().isEmpty()) {
            String fileProvinces = "src/main/java/MyStore/myfiles/province-italiane.csv";
            String fileMunicipalities = "src/main/java/MyStore/myfiles/listacomuni.csv";
            BufferedReader readerProvinces = null;
            BufferedReader readerMunicipalities = null;
            String lineProvince = "";
            String lineMunicipality = "";
            long counterProvince = 0;
            long counterMunicipality = 0;
            try {
                readerProvinces = new BufferedReader(new FileReader(fileProvinces));
                readerMunicipalities = new BufferedReader(new FileReader(fileMunicipalities));
                while ((lineProvince = readerProvinces.readLine()) != null) {
                    if (counterProvince >= 1) {
                        String[] row = lineProvince.split(";");
                        Province province = new Province(row[1], row[2], row[0]);
                        provinceRepository.save(province);
                    }
                    counterProvince++;
                }
                System.out.println("Provinces created successfully ✔️");
                while ((lineMunicipality = readerMunicipalities.readLine()) != null) {
                    if (counterMunicipality >= 1) {
                        String[] row = lineMunicipality.split(";");
                        Municipality municipality = new Municipality();
                        municipality.setCap(row[5]);
                        municipality.setName(row[9]);
                        municipality.setProvince(provinceRepository.findBySigla(row[2]).orElseThrow(() -> new NotFoundException("Province", row[2])));
                        municipalityRepository.save(municipality);
                    }
                    counterMunicipality++;
                }
                System.out.println("Municipalities created successfully ✔️");
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                readerProvinces.close();
                readerMunicipalities.close();
            }
        } else {
            System.out.println("⚠️ Provices and municipalities already uploaded ⚠️");
        }
    }
}