package com.example.redstore.repository;

import com.example.redstore.entity.Product;
import com.example.redstore.enums.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    void deleteById(long id);

    @Query(value = "select p from Product p " +
            "where p.type = (select p1.type from Product p1 where p1.id = :id) " +
            "group by p.id having p.id <> :id ")
    List<Product> getSimilar(@Param("id") long id);

    @Query("select p from Product p where p.tag = :tag")
    List<Product> getByTag(Tag tag);
}
