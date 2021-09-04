package com.example.redstore.service;

import com.example.redstore.entity.Product;
import com.example.redstore.enums.SortAlgorithmName;
import com.example.redstore.enums.Tag;
import com.example.redstore.exception.ProductNotFoundException;
import com.example.redstore.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.redstore.constants.Constants.*;

@Service
public class ProductService {

    private final ProductRepository repository;

    private final Map<String, Sort> sortAlgorithmSupplier = Map.of(
            SortAlgorithmName.PRICE_LOW.getName(), Sort.by("price").ascending(),
            SortAlgorithmName.PRICE_HIGH.getName(), Sort.by("price").descending(),
            SortAlgorithmName.RATING.getName(), Sort.by("ratio").descending(),
            SortAlgorithmName.NAME.getName(), Sort.by("name").ascending()
    );

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
        return repository.getSimilar(id).stream().limit(4)
                .collect(Collectors.toUnmodifiableList());
    }

    public Page<Product> getPaginated(int page, String algorithm) {
        if (!algorithm.isBlank()) {
            Pageable pageable = PageRequest.of(page - 1, PRODUCT_COUNT_PER_PAGE, sort(algorithm));
            return this.repository.findAll(pageable);
        }
        Pageable pageable = PageRequest.of(page - 1, PRODUCT_COUNT_PER_PAGE);
        return this.repository.findAll(pageable);
    }

    private Sort sort(String algorithmName) {
        return sortAlgorithmSupplier.get(algorithmName);
    }

    public List<Product> getByTag(Tag tag) {
        return repository.getByTag(tag);
    }
}
