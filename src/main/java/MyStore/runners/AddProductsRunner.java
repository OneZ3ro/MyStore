package MyStore.runners;

import MyStore.entities.Category;
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
                        String[] row = lineProduct.split("\"");
                        if (counter >= 1 && fileBytes > 100) {
                            String name = row[1];
                            String[] row2 = row[2].split(",");
                            String mainCategoryName = row2[1];
                            String subCategoryName = row2[2];
                            String image = row2[3];
                            double rating = Double.parseDouble(row2[5]);
                            String[] appNumbOfRating = row[3].split(",");
                            long numbOfRating = Long.parseLong(appNumbOfRating[0]+appNumbOfRating[1]);
                            String[] appDiscountPrice = row[5].split("₹")[1].split(",");
                            double discountPrice = Long.parseLong(appDiscountPrice[0]+appDiscountPrice[1]) * 0.011;
                            String[] appActualPrice = row[7].split("₹")[1].split(",");
                            double actualPrice = Long.parseLong(appActualPrice[0]+appActualPrice[1]) * 0.011;
                            if (counter <= 100) {
                                ProductDatasetDTO product = new ProductDatasetDTO(name, mainCategoryName, subCategoryName, image, rating, numbOfRating, discountPrice, actualPrice, defaultSeller);
                                productService.saveProductDataset(product);
                            }
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
