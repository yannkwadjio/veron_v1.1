package com.veron.controller.api;

import com.veron.dto.ProductDto;
import com.veron.entity.Product;
import com.veron.services.interfaces.ProductInterfaces;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products/")
@RequiredArgsConstructor
public class ProductApi {
    private final ProductInterfaces productInterfaces;

    @PostMapping("create")
    public Map<String,String> createProducts(@RequestBody ProductDto productDto){
        return productInterfaces.createProducts(productDto);
    }

    @GetMapping("get-all")
    public List<Product> getAllProduct(){
        return productInterfaces.getAllProduct();
    }

    @GetMapping("get-by-name/{name}")
    public Product getProductByName(@PathVariable("name") String name){
        return productInterfaces.getProductByName(name);
    }

    @GetMapping("get-by-ref/{refProduct}")
    public Product getProductByRef(@PathVariable("refProduct") String refProduct){
        return productInterfaces.getProductByRef(refProduct);
    }

    @PutMapping("update-by-name")
    public Map<String,String> updateProductByName(@RequestParam("name") String name,@RequestParam double purchasePrice,@RequestParam double sellingPrice){
        return productInterfaces.updateProductByName(name,purchasePrice,sellingPrice);
    }

    @PutMapping("update-by-ref")
    public Map<String,String> updateProductByRef(@RequestParam("refProduct") String refProduct,@RequestParam double purchasePrice,@RequestParam double sellingPrice){
        return productInterfaces.updateProductByRef(refProduct,purchasePrice,sellingPrice);
    }

    @DeleteMapping("/delete-by-refProduct/{refProduct}")
    public void deleteProductByRef(@PathVariable String refProduct){
        productInterfaces.deleteProductByRef(refProduct);
    }

}
