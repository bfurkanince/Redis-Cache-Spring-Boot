package com.redisdemo.respository;

import com.redisdemo.constant.ProjectConstant;
import com.redisdemo.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDao.class);

    @Autowired
    private RedisTemplate template;

    public Product save(Product product){
        template.opsForHash().put(ProjectConstant.HASH_KEY,product.getId(),product);
        return product;
    }

    public List<Product> findAll(){
        return template.opsForHash().values(ProjectConstant.HASH_KEY);
    }

    public Product findProductById(int id){
        LOG.info("called findProductById() from DB");
        return (Product) template.opsForHash().get(ProjectConstant.HASH_KEY,id);
    }

    public String deleteProduct(int id){
        template.opsForHash().delete(ProjectConstant.HASH_KEY,id);
        return "product removed !!";
    }
}
