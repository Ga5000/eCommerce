package com.api.ga5000.ecommerce.util;


import com.api.ga5000.ecommerce.dtos.AddProductDTO;
import com.api.ga5000.ecommerce.dtos.UpdateProductDTO;
import com.api.ga5000.ecommerce.entities.Product;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    Product toProduct(AddProductDTO addProductDTO);
    void toProduct(UpdateProductDTO updateProductDTO, @MappingTarget Product product);
}
