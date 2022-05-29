package com.accenture.transactionapplication.service.product;

import com.accenture.model.Product;
import com.accenture.transactionapplication.service.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ProductRepository extends BaseRepository<Product, Integer> {
    //  Find a Product by id
    @Query("select p from Product p where p.id=:id")
    Product findProductById(@Param("id") Integer id);
    //    Delete a Product by id
    @Modifying
    @Transactional
    @Query(value = "delete from products where id=:id", nativeQuery = true)
    Integer deleteProductById(@Param("id") Integer id);
    // Filter Products by id, created date, description, modified date and name
    @Query(value = "SELECT * FROM products WHERE (:id IS NULL OR id =:id) " +
            "AND (:created_at IS NULL OR created_at=:created_at) " +
            "AND (:description IS NULL OR product_description LIKE CONCAT('%',:description,'%')) " +
            "AND (:modified_at IS NULL OR modified_at=:modified_at) " +
            "AND (:name IS NULL OR product_name LIKE CONCAT('%',:name,'%'))", nativeQuery = true)
    List<Product> findAllByCreatedAtAndDescriptionAndModifiedAtAndName(
            @Param("id") Integer id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("created_at") LocalDateTime createdAt,
            @Param("modified_at") LocalDateTime modifiedAt,
            Pageable page);
}
