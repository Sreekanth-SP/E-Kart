package com.kart.service;

import com.kart.model.Product;
import com.kart.repository.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    IProductRepo productRepo;


    public List<Product> getAllProducts() {
        return  productRepo.findAll();
    }

    public String createProduct(Product product) {
        productRepo.save(product);
        return "Product added";
    }

    public Product getProductById(long id) {
        return productRepo.findById(id).orElse(null);
    }

    public String updateProduct(long id, Product productDetails) {
        Product updateProduct = getProductById(id);
        if(updateProduct == null){
            return "Employee not exist with id: " + id;
        }

        updateProduct.setProductName(productDetails.getProductName());
        updateProduct.setProductDesc(productDetails.getProductDesc());
        updateProduct.setProductRatings(productDetails.getProductRatings());
        updateProduct.setProductPrize(productDetails.getProductPrize());
        updateProduct.setProductImage(productDetails.getProductImage());

        productRepo.save(updateProduct);

        return "Product updated";
    }

    public String deleteProduct(long id) {
        Product updateProduct = getProductById(id);
        if(updateProduct == null){
            return "Employee not exist with id: " + id;
        }
        productRepo.deleteById(id);
        return "Product deleted!!!";
    }
}
