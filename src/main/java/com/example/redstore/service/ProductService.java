package com.example.redstore.service;

import com.example.redstore.entity.Product;
import com.example.redstore.entity.sort.AlgorithmFactory;
import com.example.redstore.exception.ProductNotFoundException;
import com.example.redstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.redstore.constants.Constants.*;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format(WITH_WAS_NOT_FOUND, PRODUCT_ATTRIBUTE, "id", id)));
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    public List<Product> getSimilarProducts(long id) {
        var product = getById(id);
        return getAll().stream()
                .filter(p -> p.getType() == product.getType())
                .filter(p -> p.getId() != product.getId())
                .limit(4).collect(Collectors.toList());
    }

    public List<Product> sort(String algorithm) {
        return new AlgorithmFactory(algorithm).sort(getAll());
    }

    //determines how many products will be on each page
    public List<Product> getPageProducts(int page) {
        var productCount = getAll().size();
        return productCount >= PRODUCT_COUNT_PER_PAGE * page
                ? getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, page * PRODUCT_COUNT_PER_PAGE)
                : getAll().subList((page - 1) * PRODUCT_COUNT_PER_PAGE, productCount);
    }
}
