package com.redisdemo.controller;

import com.redisdemo.constant.ProjectConstant;
import com.redisdemo.entity.Product;
import com.redisdemo.respository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @PostMapping
    public Product save(@RequestBody Product product){
        return productDao.save(product);
    }

    @GetMapping
    public List<Product> findAllProducts(){
        return productDao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key= "#id" , value = ProjectConstant.HASH_KEY , unless = "#result.price > 1000")//Price'ı 1000'den fazla olanlar Cache'den gelmesin!
    public Product findProduct(@PathVariable int id){
        return productDao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        return productDao.deleteProduct(id);
    }

}
