package MyStore;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Slf4j
public class MyRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String fileProvinces = "src/main/java/MyStore/myfiles\\provincia_regione_sigla.csv";
        String fileMunicipalities = "src/main/java/MyStore/myfiles\\listacomuni.csv";
        BufferedReader readerProvinces = null;
        BufferedReader readerMunicipalities = null;
        String lineProvince = "";
        String lineMunicipality = "";
        try {
            readerProvinces = new BufferedReader(new FileReader(fileProvinces));
            readerMunicipalities = new BufferedReader(new FileReader(fileMunicipalities));
            int counterProvinces = 1;
            int counterMunicipalities = 1;
            while((lineProvince = readerProvinces.readLine()) != null) {
//                Provincia;Regione;Sigla
                String[] row = lineProvince.split(";");
                System.out.println("Nome Provincia: " + row[0]);
                System.out.println("Nome Regione: " + row[1]);
                System.out.println("Sigla: " + row[2]);
            }
            while((lineMunicipality = readerMunicipalities.readLine()) != null) {
//                Istat;Comune;Provincia;Regione;Prefisso;CAP;CodFisco;Abitanti;Link
                String[] row = lineMunicipality.split(";");
                System.out.println("Nome Comune: " + row[1]);
                System.out.println("Nome Cap: " + row[5]);
                System.out.println("Prendere il nome dalla classe Province: " + row[2]);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            readerProvinces.close();
            readerMunicipalities.close();
        }
    }
}
