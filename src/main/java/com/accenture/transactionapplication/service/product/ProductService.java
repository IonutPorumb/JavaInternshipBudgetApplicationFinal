package com.accenture.transactionapplication.service.product;

import com.accenture.model.Product;
import com.accenture.transactionapplication.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService extends BaseService<Product, Integer> {
    @Override
    public boolean validateEntity(Product entity) {
        return false;
    }

    private final ProductRepository productRepository;
    //  Find a Product by id
    public Product findProductById(Integer id) {
        return productRepository.findProductById(id);
    }
    //    Delete a Product by id
    public Integer deleteProductById(Integer id) {
        return productRepository.deleteProductById(id);
    }
    // Update a Product by id
    public Product updateProductById(Product product, Integer id) {
        if (productRepository.findById(id).isPresent()) {
            Product existingProduct = productRepository.getById(id);
            LocalDateTime modificationDate = LocalDateTime.now();
            existingProduct.setDescription(product.getDescription());
            existingProduct.setName(product.getName());
            existingProduct.setCreatedAt(product.getCreatedAt());
            existingProduct.setModifiedAt(modificationDate);
            return productRepository.save(existingProduct);
        } else {
            System.out.println("There is no product available with the required id number");
            return null;
        }
    }
    // POST /products - adds a new products
    public Product insertNewProduct(Product product) {
        LocalDateTime creationDate = LocalDateTime.now();
        Product newProduct = new Product(product.getName(), product.getDescription(), creationDate,
                null);
        return productRepository.save(newProduct);
    }
    // Filter Products by id, created date, description, modified date and name
    public List<Product> findAllByCreatedAtAndDescriptionAndModifiedAtAndName(Integer id, String name, String description,
                                                                              LocalDateTime createdAt, LocalDateTime modifiedAt, Integer page, Integer size) {
        return productRepository.findAllByCreatedAtAndDescriptionAndModifiedAtAndName(id, name, description, createdAt,
                modifiedAt, PageRequest.of(page, size, Sort.by("product_name").ascending()));
    }
}
