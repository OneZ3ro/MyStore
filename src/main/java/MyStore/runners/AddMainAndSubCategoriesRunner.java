package MyStore.runners;

import MyStore.entities.MainCategory;
import MyStore.entities.SubCategory;
import MyStore.services.MainCategoryService;
import MyStore.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(2)
public class AddMainAndSubCategoriesRunner implements CommandLineRunner {
    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Override
    public void run(String... args) throws Exception {
        if (mainCategoryService.getMainCategories().isEmpty() && subCategoryService.getSubCategories().isEmpty()) {
            String fileMainCategory = "src/main/java/MyStore/myfiles/products_main_categories.csv";
            BufferedReader readerMainCategory = new BufferedReader(new FileReader(fileMainCategory));
            String fileSubCategory = "src/main/java/MyStore/myfiles/products_sub_categories.csv";
            BufferedReader readerSubCategory = new BufferedReader(new FileReader(fileSubCategory));
            String lineMainCategory = "";
            String lineSubCategory = "";
            int counterMainCategory = 0;
            int counterSubCategory = 0;
            try {
                while ((lineMainCategory = readerMainCategory.readLine()) != null) {
                    if (counterMainCategory >= 1) {
                        String[] row = lineMainCategory.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        MainCategory mainCategory = new MainCategory(Long.parseLong(row[0]), row[1]);
                        mainCategoryService.saveMainCategory(mainCategory);
                    }
                    counterMainCategory++;
                }
                System.out.println("MainCategories created successfully ✔️");
                while ((lineSubCategory = readerSubCategory.readLine()) != null) {
                    if (counterSubCategory >= 1) {
                        String[] row = lineSubCategory.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                        SubCategory subCategory = new SubCategory(Long.parseLong(row[0]), row[1], mainCategoryService.getMainCategoryById(Long.parseLong(row[2])));
                        subCategoryService.saveSubCategory(subCategory);
                    }
                    counterSubCategory++;
                }
                System.out.println("SubCategories created successfully ✔️");
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                readerMainCategory.close();
                readerSubCategory.close();
            }
        } else {
            System.out.println("⚠️ MainCategories and SubCategories already uploaded ⚠️");
        }
    }
}
