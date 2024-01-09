package MyStore.runners;

import MyStore.entities.Product;
import MyStore.entities.User;
import MyStore.enums.Role;
import MyStore.repositories.ProductRepository;
import MyStore.repositories.UserRepository;
import MyStore.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@Order(3)
public class AddProductsRunner implements CommandLineRunner {
    @Value("${my.secret.password}")
    private String mySecretPassword;

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ResidentService residentService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.findAll().isEmpty()) {
            User user = createDefaultUser();
            String fileProduct = "src/main/java/MyStore/myfiles/all_products.csv";
            String defaultSeller = "MyStore";
            BufferedReader readerProduct = new BufferedReader(new FileReader(fileProduct));
            String lineProduct = "";
            int counterProduct = 0;
            try {
                while ((lineProduct = readerProduct.readLine()) != null) {
                    if (counterProduct >= 1) {
                        String[] row = lineProduct.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        Product product = new Product();
                        product.setName(row[1].split("\"").length > 1 ? row[1].split("\"")[1] : row[1]);
                        product.setImgUrl(row[2]);
                        product.setStars(Double.parseDouble(row[4]));
                        product.setReviews(Long.parseLong(row[5]));
                        product.setPrice(Double.parseDouble(row[6]));
                        product.setListPrice(Double.parseDouble(row[7]) != 0.0 ? Double.parseDouble(row[7]) : Double.parseDouble(row[6]));
                        product.setSubCategory(subCategoryService.getSubCategoryById(Long.parseLong(row[8])));
                        product.setBestSeller(Boolean.parseBoolean(row[9].toLowerCase()));
                        product.setBoughtInLastMonth(Long.parseLong(row[10]));
                        product.setSeller(defaultSeller);
                        product.setUserSeller(user);
                        productRepository.save(product);
                    }
                    counterProduct++;
                }
                System.out.println("Products created successfully ✔️");
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                readerProduct.close();
            }
        } else {
            System.out.println("⚠️ Products already uploaded ⚠️");
        }
    }

    public User createDefaultUser() {
        User user = new User();
        user.setName("Angelo");
        user.setSurname("Moreno");
        user.setUsername("AngMor");
        user.setEmail("angmor@gmail.com");
        user.setPassword(bcrypt.encode(mySecretPassword));
        user.setBorn(LocalDate.parse("2002-01-01"));
        user.setResident(residentService.getResidentByCap("20900"));
        user.setAddress("Via a caso, 13");
        user.setRoles(Arrays.asList(Role.USER, Role.ADMIN));
        return userRepository.save(user);
    }
}
