package vttp.shoppingcart.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Optional<String> getCartList(String name) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        String list = (String) ops.get(name);

        if (list == null) {
            return Optional.empty();    // empty box
        }
        return Optional.of(list); 

    }
}
