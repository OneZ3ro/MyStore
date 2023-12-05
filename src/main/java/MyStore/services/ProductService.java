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
import MyStore.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserService userService;

    @Autowired
    private SubCategoryService subCategoryService;

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

    public Product saveProduct(ProductDTO body, UUID userId) throws IOException {
        Product newProduct = new Product();
        newProduct.setName(body.name());
        newProduct.setImgUrl(body.imgUrl());
        newProduct.setPrice(body.price() != 0.0 ? body.price() : body.listPrice());
        newProduct.setListPrice(body.listPrice());
        newProduct.setBestSeller(false);
        newProduct.setSeller(body.seller());
        newProduct.setSubCategory(subCategoryService.getSubCategoryByName(body.subCategory()));
        newProduct.setUserSeller(userService.getUserById(userId));
        return productRepository.save(newProduct);
    }

//    public void saveProductDataset(ProductDatasetDTO body) {
//        Product newProduct = new Product();
//        newProduct.setName(body.name());
//        newProduct.setImgUrl(body.imgUrl());
//        newProduct.setStars(body.starts());
//        newProduct.setReviews(body.reviews());
//        newProduct.setPrice(body.price() != 0.0 ? body.price() : body.listPrice());
//        newProduct.setListPrice(body.listPrice());
//        newProduct.setSubCategory(subCategoryService.getSubCategoryByName(body.subCategory()));
//        newProduct.setBestSeller(body.bestSeller());
//        newProduct.setBoughtInLastMonth(body.boughtInLastMonth());
//        newProduct.setSeller(body.seller());
//        newProduct.setUserSeller(userService.getUserById(UUID.fromString(defaultUserId)));
//        productRepository.save(newProduct);
//    }

    public Product updateProductById (UUID productId, ProductDTO body) throws NotFoundException {
        Product productFound = this.getProductById(productId);
        productFound.setName(body.name());
        productFound.setSeller(body.seller());
        productFound.setName(body.name());
        productFound.setImgUrl(body.imgUrl());
        productFound.setPrice(body.price() != 0.0 ? body.price() : body.listPrice());
        productFound.setListPrice(body.listPrice());
        productFound.setSeller(body.seller());
        productFound.setSubCategory(subCategoryService.getSubCategoryByName(body.subCategory()));
        productFound.setUserSeller(userService.getUserById(body.userSeller()));
        return productRepository.save(productFound);
    }
}
