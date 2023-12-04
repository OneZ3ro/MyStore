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
public class AddProvinceAndMunicipalitiesRunner implements CommandLineRunner {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public void run(String... args) throws Exception {
        String fileProvinces = "src/main/java/MyStore/myfiles\\provincia_regione_sigla.csv";
        String fileMunicipalities = "src/main/java/MyStore/myfiles\\listacomuni.csv";
        BufferedReader readerProvinces = null;
        BufferedReader readerMunicipalities = null;
        String lineProvince = "";
        String lineMunicipality = "";
        if (provinceRepository.findAll().isEmpty() && municipalityRepository.findAll().isEmpty()) {
            try {
                readerProvinces = new BufferedReader(new FileReader(fileProvinces));
                readerMunicipalities = new BufferedReader(new FileReader(fileMunicipalities));
//            int counterProvinces = 1;
//            int counterMunicipalities = 1;
                while ((lineProvince = readerProvinces.readLine()) != null) {
//                Provincia;Regione;Sigla
                    String[] row = lineProvince.split(";");
                    System.out.println("Nome Provincia: " + row[0]);
                    System.out.println("Nome Regione: " + row[1]);
                    System.out.println("Sigla: " + row[2]);
                    Province province = new Province(row[0], row[1], row[2]);
                    provinceRepository.save(province);
                }
                while ((lineMunicipality = readerMunicipalities.readLine()) != null) {
//                Istat;Comune;Provincia;Regione;Prefisso;CAP;CodFisco;Abitanti;Link
                    String[] row = lineMunicipality.split(";");
                    System.out.println("Nome Comune: " + row[1]);
                    System.out.println("Nome Cap: " + row[5]);
                    System.out.println("Prendere il nome dalla classe Province: " + row[2]);
                    Municipality municipality = new Municipality();
                    municipality.setName(row[1]);
                    municipality.setCap(row[5]);
                    municipality.setProvince(provinceRepository.findBySigla(row[2]).orElseThrow(() -> new NotFoundException("Province")));
                    municipalityRepository.save(municipality);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                readerProvinces.close();
                readerMunicipalities.close();
            }
        } else {
            System.out.println("Provices and municipalities already uploaded");
        }
    }
}