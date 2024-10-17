package com.api.ga5000.ecommerce.services.interfaces;

import com.api.ga5000.ecommerce.dtos.AddProductDTO;
import com.api.ga5000.ecommerce.dtos.SearchFilter;
import com.api.ga5000.ecommerce.dtos.UpdateProductDTO;
import com.api.ga5000.ecommerce.entities.Comment;
import com.api.ga5000.ecommerce.entities.Product;

import java.awt.print.Pageable;
import java.util.List;

public interface IProductService {
    void addProduct(AddProductDTO product);
    void updateProduct(UpdateProductDTO product, Long productId);
    void deleteProduct(Long productId);
    Product getProductById(Long productId);

    List<Product> getAllProducts();
    List<Comment> getProductComments(Long productId);
    List<Product> getProductsByFilter(SearchFilter searchFilter, Pageable pageable);
}