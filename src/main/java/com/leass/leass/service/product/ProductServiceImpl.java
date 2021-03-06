package com.leass.leass.service.product;

import com.leass.leass.model.Product;
import com.leass.leass.repository.ProductRepository;
import com.leass.leass.repository.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductSpecification productSpecification;


    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public List<Product> findByRent(Boolean rent) {
        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setRent(false);
        List<Product> products = productRepository.findAll(productSpecification.invoiceByQuery(productCriteria));
        return products;
    }
}
