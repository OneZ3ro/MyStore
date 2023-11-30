package MyStore.services;

import MyStore.entities.Municipality;
import MyStore.entities.Product;
import MyStore.entities.User;
import MyStore.enums.Role;
import MyStore.exceptions.BadRequestException;
import MyStore.exceptions.NotFoundException;
import MyStore.payloads.entities.ProductDTO;
import MyStore.payloads.entities.ProductDatasetDTO;
import MyStore.payloads.entities.UserRegistrationDTO;
import MyStore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Page<Product> getProducts (int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return productRepository.findAll(pageable);
    }

    public Product getProductById (UUID productId) throws NotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product", productId));
    }

    public void deleteProductById (UUID productId) throws NotFoundException{
        productRepository.deleteById(productId);
    }

    public Product saveProduct(ProductDTO body) throws IOException {
        Product newProduct = new Product();
        newProduct.setName(body.name());
        newProduct.setMainCategory(body.mainCategory());
        newProduct.setSubCategory(body.subCategory());
        newProduct.setImage(body.image());
        newProduct.setDiscountPrice(body.discountPrice());
        newProduct.setActualPrice(body.actualPrice());
        if (newProduct.getSeller().isEmpty()) {
            newProduct.setSeller("Amazon");
        } else {
            newProduct.setSeller(body.seller());
        }
        return productRepository.save(newProduct);
    }

    public Product saveProductDataset(ProductDatasetDTO body) throws IOException {
        Product newProduct = new Product();
        newProduct.setName(body.name());
        newProduct.setMainCategory(body.mainCategory());
        newProduct.setSubCategory(body.subCategory());
        newProduct.setImage(body.image());
        newProduct.setRating(body.rating());
        newProduct.setNumbOfRating(body.numbOfRating());
        newProduct.setDiscountPrice(body.discountPrice());
        newProduct.setActualPrice(body.actualPrice());
        newProduct.setSeller(body.seller());
        return productRepository.save(newProduct);
    }

    public Product updateProductById (UUID userId, ProductDTO body) throws NotFoundException {
        Product userFound = this.getProductById(userId);
        userFound.setName(body.name());
        userFound.setMainCategory(body.mainCategory());
        userFound.setSubCategory(body.subCategory());
        userFound.setImage(body.image());
        userFound.setDiscountPrice(body.discountPrice());
        userFound.setActualPrice(body.actualPrice());
        if (userFound.getSeller().isEmpty()) {
            userFound.setSeller("Amazon");
        } else {
            userFound.setSeller(body.seller());
        }
        return productRepository.save(userFound);
    }
}
