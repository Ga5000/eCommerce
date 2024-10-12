package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.AddProductDTO;
import com.api.ga5000.ecommerce.dtos.UpdateProductDTO;
import com.api.ga5000.ecommerce.entities.Category;
import com.api.ga5000.ecommerce.entities.Comment;
import com.api.ga5000.ecommerce.entities.Product;
import com.api.ga5000.ecommerce.repositories.ProductRepository;
import com.api.ga5000.ecommerce.services.interfaces.IProductService;
import com.api.ga5000.ecommerce.util.DtoConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final DtoConverter converter;

    public ProductService(ProductRepository productRepository, DtoConverter converter) {
        this.productRepository = productRepository;
        this.converter = converter;
    }

    @Override
    public void addProduct(AddProductDTO addProductDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductDTO,product);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(UpdateProductDTO updatedProduct, Long productId) {
        Product existingProduct = getProductById(productId);
        BeanUtils.copyProperties(updatedProduct,existingProduct);
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByProductNameIgnoreCase(name);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByProductPriceBetween(min,max);
    }

    @Override
    public List<Comment> getProductComments(Long productId) {
        Product product = getProductById(productId);

        return product.getComments();
    }

    @Override
    public List<Product> getProductsInStock() {
        return productRepository.findByInventoryGreaterThan(0);
    }
}
