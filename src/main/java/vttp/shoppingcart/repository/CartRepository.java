package vttp.shoppingcart.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.shoppingcart.model.Cart;
import vttp.shoppingcart.model.Item;

@Repository
public class CartRepository {
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Retrieve cart
    public Optional<Cart> get(String name) {

        // check if empty
        if (!redisTemplate.hasKey(name)) {
            return Optional.empty();
        }

        // Retrieve items in cart if cart exists
        List<Item> contents = new LinkedList<>();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        // returns how many items in the cart
        long size = listOps.size(name);
        for (long i = 0; i < size; i++) {
            String item = listOps.index(name, i);
            contents.add(Item.create(item));
        }

        Cart cart = new Cart(name);
        cart.setContents(contents);
        return Optional.of(cart);
    }

    public void save(Cart cart) {
        String name = cart.getName();
        List<Item> contents = cart.getContents();
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        long l = listOps.size(name);

        // remove all contents in the list then populate again
        if (l > 0) {
            listOps.trim(name, 0, l);
        }
        listOps.leftPushAll(name, 
                        contents.stream().map(v -> v.toJson().toString()).toList());


    }
}
