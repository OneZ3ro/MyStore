package MyStore.controllers;

import MyStore.entities.Product;
import MyStore.entities.User;
import MyStore.payloads.entities.ProductDTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public Page<Product> getProduct(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "userId") String orderBy){
        return productService.getProducts(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable UUID productId) {
        return productService.getProductById(productId);
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
