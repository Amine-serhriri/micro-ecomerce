package com.ecommerce.micrommerce.web;

import com.ecommerce.micrommerce.web.exception.ProductIntrouvableException;
import com.ecommerce.micrommerce.model.Product;
import com.ecommerce.micrommerce.repository.ProductRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;



    @GetMapping("/Produits")
    public MappingJacksonValue listeProduit(){
        List<Product>products= productRepository.findAll();
        SimpleBeanPropertyFilter monFiltre =
                SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listSansFilter =
                new SimpleFilterProvider().addFilter("filtreDynamique",monFiltre);
        MappingJacksonValue produitFilter =
                new MappingJacksonValue(products);
        produitFilter.setFilters(listSansFilter);
        return produitFilter;
    }
    @GetMapping("/Produits/{id}")
    public MappingJacksonValue showProduct(@PathVariable int id) throws ProductIntrouvableException{
        Product product=productRepository.findById(id).orElse(null);
        if(product==null){
            throw new ProductIntrouvableException("Le produit avec l'id "+id+"est introuvable ");
        }
        SimpleBeanPropertyFilter monFiltre =
                SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listSansFilter =
                new SimpleFilterProvider().addFilter("filtreDynamique",monFiltre);
        MappingJacksonValue produitFilter =
                new MappingJacksonValue(product);
        produitFilter.setFilters(listSansFilter);
        return produitFilter;

    }
    @PostMapping("/Produitss")
    public ResponseEntity<Product> saveProduct(@Valid  @RequestBody Product product){
        Product productAdded=productRepository.save(product);
        if(Objects.isNull(productAdded)){
            return  ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping("/AdminProduits")
    public HashMap<Product,Integer> calculerMargeProduit(){
        HashMap<Product,Integer>marge2=new HashMap<>();
        List<Product> products =productRepository.findAll();
        products.forEach(product -> {
            marge2.put(product,product.getPrix()- product.getPrixAchat());
        });

        return marge2;
    }
}
