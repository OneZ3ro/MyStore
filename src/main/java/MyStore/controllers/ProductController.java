package MyStore.controllers;

import MyStore.entities.MainCategory;
import MyStore.entities.Product;
import MyStore.entities.SubCategory;
import MyStore.entities.User;
import MyStore.payloads.entities.ProductDTO;
import MyStore.services.MainCategoryService;
import MyStore.services.ProductService;
import MyStore.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("")
    public Page<Product> getProduct(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "12") int size,
                                    @RequestParam(defaultValue = "productId") String orderBy){
        return productService.getProducts(page, size, orderBy);
    }

    @GetMapping("/name/{name}")
    public Page<Product> getProductByName(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "12") int size,
                                    @RequestParam(defaultValue = "productId") String orderBy,
                                          @PathVariable String name){
        return productService.getProductsByName(page, size, orderBy, name);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/mainCategories")
    public List<MainCategory> getMainCategories() {
        return mainCategoryService.getMainCategories();
    }

    @GetMapping("/mainCategories/{mainCategName}")
    public Page<Product> getProductByMainCategName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
                                                   @RequestParam(defaultValue = "productId") String orderBy, @PathVariable String mainCategName){
        return productService.getProductsByMainCategory(page, size, orderBy, mainCategName);
    }

    @GetMapping("/subCategories")
    public List<SubCategory> getSubCategories() {
        return subCategoryService.getSubCategories();
    }

    @GetMapping("/subCategories/{subCategName}")
    public Page<Product> getProductBySubCategName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "12") int size,
                                                   @RequestParam(defaultValue = "productId") String orderBy, @PathVariable String subCategName){
        return productService.getProductBySubCategName(page, size, orderBy, subCategName);
    }

    @PostMapping("")
    public Product saveProduct(@AuthenticationPrincipal User currentUser, @RequestBody @Validated ProductDTO body) {
        try {
            return productService.saveProduct(body, currentUser.getUserId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Product updateProduct(@PathVariable UUID productId, @RequestBody @Validated ProductDTO body) {
        return productService.updateProductById(productId, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProductById(productId);
    }

}
