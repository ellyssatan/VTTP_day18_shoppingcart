package vttp.shoppingcart.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private List<String> cart = new LinkedList<>();

    public void add(String item) {
        cart.add(item);
    }
    
    public void populate(String s) {

    }

    public String serialise(List<String> c) {
        return String.join(",", c);

    }

    public List<String> deserialise(String s) {
        String[] items = s.split(",");
        for (String i : items) {
            cart.add(i);
        }
        return cart;

    }
}
