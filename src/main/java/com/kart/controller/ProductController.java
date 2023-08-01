package com.kart.controller;

import com.kart.model.Product;
import com.kart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // build create employee REST API
    @PostMapping
    public String createProduct(@RequestBody Product product) {

        return productService.createProduct(product);
    }

    // build get employee by id REST API
    @GetMapping("{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

    // build update employee REST API
    @PutMapping("{id}")
    public String updateProduct(@PathVariable long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id,productDetails);
    }

    // build delete employee REST API
    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
