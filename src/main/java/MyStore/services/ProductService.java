package MyStore.services;

import MyStore.entities.Product;
import MyStore.exceptions.NotFoundException;
import MyStore.payloads.entities.ProductDTO;
import MyStore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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

    public List<Product> getAllProducts () {
        return productRepository.findAll();
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

    public Page<Product> getProductsByMainCategory (int page, int size, String orderBy, String mainCatName) {
        List<Product> filteredProducts = productRepository.findAll().stream().filter(product -> product.getSubCategory().getMainCategory().getMainCategoryName().toLowerCase().equals(mainCatName.toLowerCase())).toList();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(orderBy));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), filteredProducts.size());
        return new PageImpl<>(filteredProducts.subList(start, end), pageRequest, filteredProducts.size());
    }

    public Page<Product> getProductsByName (int page, int size, String orderBy, String name) {
        List<Product> filteredProducts = productRepository.findByNameContaining(name).orElseThrow(() -> new NotFoundException("Product name", name));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(orderBy));
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), filteredProducts.size());
        return new PageImpl<>(filteredProducts.subList(start, end), pageRequest, filteredProducts.size());
    }
}
