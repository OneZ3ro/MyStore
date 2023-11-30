package MyStore.runners;

import MyStore.entities.Product;
import MyStore.payloads.entities.ProductDTO;
import MyStore.payloads.entities.ProductDatasetDTO;
import MyStore.repositories.ProductRepository;
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
    @Override
    public void run(String... args) throws Exception {
        String productsDirectory = "src/main/java/MyStore/myfiles/Amazon_Products";
        String defaultSeller = "Amazon";
        BufferedReader readerProduct = null;
        String lineProduct = "";
        File folderProducts = new File(productsDirectory);
        File[] listOfFiles = folderProducts.listFiles();
        List<String> listnameunder1000 = new ArrayList<>();
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
                        if (counter > 1) {
//                            String rowNumbRatings = row[6]+row[7];
//                            String row8E9 = row[8]+row[9];
//                            String row10e11 = row[10]+row[11];
//                            System.out.println(rowNumbRatings);
//                            System.out.println(row8E9);
//                            System.out.println(row10e11);
//
//                            long discountedPriceIndia = Long.parseLong(row8E9.split("\"")[1].split("₹")[1]);
//                            long actuallyPriceIndia = Long.parseLong(row10e11.split("\"")[1].split("₹")[1]);
//                            double discountedPriceEuro = discountedPriceIndia * 0.011;
//                            double actuallyPriceEuro = actuallyPriceIndia * 0.011;
//                            System.out.println(discountedPriceEuro);
//                            System.out.println(actuallyPriceEuro);
//                            if (counter > 100 || fileBytes < 100) {
                            String name = row[1];
                            String[] row2 = row[2].split(",");
                            String mainCategory = row2[1];
                            String subCategory = row2[2];
                            String image = row2[3];
                            double rating = Double.parseDouble(row2[5]);
                            String[] appNumbOfRating = row[3].split(",");
                            long numbOfRating = Long.parseLong(appNumbOfRating[0]+appNumbOfRating[1]);
                            String[] appDiscountPrice = row[5].split("₹")[1].split(",");
                            double discountPrice = Long.parseLong(appDiscountPrice[0]+appDiscountPrice[1]) * 0.011;
                            String[] appActualPrice = row[7].split("₹")[1].split(",");
                            double actualPrice = Long.parseLong(appActualPrice[0]+appActualPrice[1]) * 0.011;
//                            System.out.println("name: " + name);
//                            System.out.println("mainCategory: " + mainCategory);
//                            System.out.println("subCategory: " + subCategory);
//                            System.out.println("image: " + image);
//                            System.out.println("rating: " + rating);
//                            System.out.println("numbOfRating: " + numbOfRating);
//                            System.out.println("discountPrice: " + discountPrice);
//                            System.out.println("actualPrice: " + actualPrice);
                            ProductDatasetDTO product = new ProductDatasetDTO(name, mainCategory, subCategory, image, rating, numbOfRating, discountPrice, actualPrice, defaultSeller);
                            productService.saveProductDataset(product);
                            break forloop;
                        }
                        counter++;
                        }

                    if (fileBytes < 100) {
                        String[] fileNameSplitted = fileName.split("\\.");
                        listnameunder1000.add(fileNameSplitted[0]);
                    }
//                    if (i == listOfFiles.length - 1) {
//                        listnameunder1000.forEach(System.out::println);
//                        System.out.println(listnameunder1000.size());
//                    }
//                    if (fileName.equals("Refurbished and Open Box.csv")) {
//                        System.out.println(fileBytes);
//                    }
//                    System.out.println(fileName + " primo prodotto: " + secondoElemento + "\nBytes: " + fileBytes);

                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            readerProduct.close();
        }
    }
}
