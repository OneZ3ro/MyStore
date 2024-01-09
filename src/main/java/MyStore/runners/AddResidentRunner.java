package MyStore.runners;

import MyStore.entities.MainCategory;
import MyStore.entities.Resident;
import MyStore.entities.SubCategory;
import MyStore.repositories.ResidentRepository;
import MyStore.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(2)
public class AddResidentRunner implements CommandLineRunner {
    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private ResidentService residentService;

    @Override
    public void run(String... args) throws Exception {
        if (residentService.getResidents().isEmpty()) {
            String fileResidents = "src/main/java/MyStore/myfiles/municipalityList.csv";
            BufferedReader readerResidents = new BufferedReader(new FileReader(fileResidents));
            String lineResidents = "";
            int counterResidents = 0;
            try {
                while ((lineResidents = readerResidents.readLine()) != null) {
                    if (counterResidents >= 1) {
                        String[] row = lineResidents.split(";");
                        Resident resident = new Resident();
                        resident.setRegion(row[9]);
                        resident.setProvince(row[6]);
                        resident.setAcronym(row[5]);
                        resident.setCap(row[4]);
                        resident.setMunicipality(row[2]);
                        residentRepository.save(resident);
                    }
                    counterResidents++;
                }
                System.out.println("Residents created successfully ✔️");
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                readerResidents.close();
            }
        } else {
            System.out.println("⚠️ Residents already uploaded ⚠️");
        }
    }
}
