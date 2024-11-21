package com.veron.services.interfaces;

import com.veron.dto.ProductDto;
import com.veron.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductInterfaces {
    Map<String, String> createProducts(ProductDto productDto);

    List<Product> getAllProduct();

    Product getProductByName(String name);


    Map<String, String> updateProductByName(String name,double purchasePrice,double sellingPrice);

    Product getProductByRef(String refProduct);

    Map<String, String> updateProductByRef(String refProduct, double purchasePrice, double sellingPrice);

    void deleteProductByRef(String refProduct);
}
