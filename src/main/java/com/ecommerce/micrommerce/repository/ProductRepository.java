package com.ecommerce.micrommerce.repository;

import com.ecommerce.micrommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
