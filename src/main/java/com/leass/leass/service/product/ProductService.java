package com.leass.leass.service.product;

import com.leass.leass.model.Product;

import java.util.List;

public interface ProductService {

    void save(Product product);

    List<Product> findAll();

    List<Product> findByRent(Boolean rent);
}
