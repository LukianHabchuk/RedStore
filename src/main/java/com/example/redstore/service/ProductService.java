package com.example.redstore.service;

import com.example.redstore.entity.Product;
import com.example.redstore.enums.SortAlgorithmName;
import com.example.redstore.exception.ProductNotFoundException;
import com.example.redstore.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Product> getPaginated(int page, String algorithm) {
        var sort = sort(algorithm);
        if (sort != null) {
            Pageable pageable = PageRequest.of(page-1, PRODUCT_COUNT_PER_PAGE, sort);
            return this.repository.findAll(pageable);
        }
        Pageable pageable = PageRequest.of(page-1, PRODUCT_COUNT_PER_PAGE);
        return this.repository.findAll(pageable);
    }

    public Sort sort(String algorithmName) {
        if (algorithmName.equals(SortAlgorithmName.PRICE_LOW.getName()))
            return Sort.by("price").ascending();
        if (algorithmName.equals(SortAlgorithmName.PRICE_HIGH.getName()))
            return Sort.by("price").descending();
        if (algorithmName.equals(SortAlgorithmName.RATING.getName()))
            return Sort.by("ratio").descending();
        if (algorithmName.equals(SortAlgorithmName.NAME.getName()))
            return Sort.by("name").ascending();
        return null;
    }
}
