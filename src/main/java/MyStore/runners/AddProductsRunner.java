package MyStore.runners;

import MyStore.entities.Category;
import MyStore.entities.Product;
import MyStore.payloads.entities.ProductDatasetDTO;
import MyStore.repositories.CategoryRepository;
import MyStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class AddProductsRunner implements CommandLineRunner {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        String productsDirectory = "src/main/java/MyStore/myfiles/Amazon_Products";
        String defaultSeller = "Amazon";
        BufferedReader readerProduct = null;
        String lineProduct = "";
        File folderProducts = new File(productsDirectory);
        File[] listOfFiles = folderProducts.listFiles();
        List<String> appMainSubList = new ArrayList<>();
        try {
            forloop:
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String fileName = listOfFiles[i].getName();
                    long fileBytes = listOfFiles[i].length();
                    readerProduct = new BufferedReader(new FileReader(productsDirectory + "\\" + fileName));
                    int counter = 0;
                    whileloop:
                    while ((lineProduct = readerProduct.readLine()) != null) {
                        String[] row = lineProduct.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        System.out.println("Length row: " + row.length);
                        if (counter >= 1 && fileBytes > 100) {
                            if (counter == 101) {
                                break whileloop;
                            }
                            if (counter <= 100) {
                                Arrays.stream(row).forEach(System.out::println);
                                String name = row[0];
                                String mainCategoryName = row[1];
                                String subCategoryName = row[2];
                                String image = row[3];
                                double rating = row[5].isEmpty() ? 0.0 : Double.parseDouble(row[5]);
                                long numbOfRating;
                                double discountPrice;
                                double actualPrice;
                                if (!row[6].isEmpty()) {
                                    if (row[6].charAt(0) == '\"') {
                                        String[] appNumbOfRating = row[6].split("\"")[1].split(",");
                                        numbOfRating = Long.parseLong(appNumbOfRating[0] + appNumbOfRating[1]);
                                    } else {
                                        numbOfRating = Long.parseLong(row[6]);
                                    }
                                } else {
                                    numbOfRating = 0;
                                }
                                if (!row[7].isEmpty()) {
                                    if (row[7].charAt(0) == '\"') {
                                        String[] appDiscountPrice = row[7].split("\"")[1].split("₹")[1].split(",");
                                        discountPrice = Double.parseDouble(appDiscountPrice[0] + appDiscountPrice[1]) * 0.011;
                                    } else {
                                        discountPrice = Double.parseDouble(row[7].split("₹")[1]) * 0.011;
                                    }
                                } else {
                                    discountPrice = 0.0;
                                }
                                if (!row[8].isEmpty()) {
                                    if (row[8].charAt(0) == '\"') {
                                        String[] appActualPrice = row[8].split("\"")[1].split("₹")[1].split(",");
                                        actualPrice = Double.parseDouble(appActualPrice[0] + appActualPrice[1]) * 0.011;
                                    } else {
                                        actualPrice = Double.parseDouble(row[8].split("₹")[1]) * 0.011;
                                    }
                                } else {
                                    actualPrice = 0.0;
                                }
                                ProductDatasetDTO product = new ProductDatasetDTO(name, mainCategoryName, subCategoryName, image, rating, numbOfRating, discountPrice, actualPrice, defaultSeller);
                                productService.saveProductDataset(product);
                                String nameMainSub = mainCategoryName + ","  + subCategoryName;
                                if (appMainSubList.isEmpty()){
                                    appMainSubList.add(nameMainSub);
                                } else {
                                    for (int j = 0; j < appMainSubList.size(); j++) {
                                        if (!appMainSubList.get(j).equals(nameMainSub)) {
                                            appMainSubList.add(nameMainSub);
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("Giro for: " + i + ", giro while: " + counter);
                        counter++;
                    }
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            for (int i = 0; i < appMainSubList.size(); i++) {
                String[] appMain = appMainSubList.get(i).split(",");
                List<String> appSub = new ArrayList<>();
                for (int k = 0; k < appMainSubList.size(); k++) {
                    String[] appMain2 = appMainSubList.get(k).split(",");
                    if (appMain[0].equals(appMain2[0])) {
                        appSub.add(appMain2[1]);
                    }
                }
                if (i == 0) {
                    Category category = new Category(appMain[0], appSub);
                    categoryRepository.save(category);
                } else {
                    if (categoryRepository.findByMainCategoryName(appMain[0]).isEmpty()) {
                        Category category = new Category(appMain[0], appSub);
                        categoryRepository.save(category);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            readerProduct.close();
        }
    }
}
