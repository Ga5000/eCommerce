package com.api.ga5000.ecommerce.services;

import com.api.ga5000.ecommerce.dtos.AddProductDTO;
import com.api.ga5000.ecommerce.dtos.SearchFilter;
import com.api.ga5000.ecommerce.dtos.UpdateProductDTO;
import com.api.ga5000.ecommerce.entities.Comment;
import com.api.ga5000.ecommerce.entities.Product;
import com.api.ga5000.ecommerce.repositories.ProductRepository;
import com.api.ga5000.ecommerce.services.interfaces.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public void addProduct(AddProductDTO addProductDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductDTO, product);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void updateProduct(UpdateProductDTO updatedProduct, Long productId) {
        Product existingProduct = getProductById(productId);
        BeanUtils.copyProperties(updatedProduct, existingProduct);
        productRepository.save(existingProduct);
    }

    @Transactional
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
    public List<Comment> getProductComments(Long productId) {
        Product product = getProductById(productId);
        return product.getComments();
    }

    @Override
    public List<Product> getProductsByFilter(SearchFilter searchFilter, Pageable pageable) {
       Page<Product> products = productRepository.findByFilters(searchFilter,pageable);

       return products.getContent();
    }
}
