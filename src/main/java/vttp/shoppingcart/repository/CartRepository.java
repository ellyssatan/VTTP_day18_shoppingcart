package vttp.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.shoppingcart.models.Cart;

@Repository
public class CartRepository {
    
    // @Autowired
    // private RedisTemplate<String, String> redisTemplate;

    // public Optional<String> getCartList(String name) {
    //     ListOperations<String, String> ops = redisTemplate.opsForList();
    //     Cart c = new Cart();
    //     c.getUser()
    //     String list =  ops;

    //     if (list == null) {
    //         return Optional.empty();    // empty box
    //     }
    //     return Optional.of(list); 

    // }
}

