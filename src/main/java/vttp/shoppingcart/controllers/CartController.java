package vttp.shoppingcart.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.shoppingcart.model.Cart;
import vttp.shoppingcart.model.Item;
import vttp.shoppingcart.repository.CartRepository;

@Controller
@RequestMapping(path = {"", "/"})
public class CartController {

    @Autowired
    private CartRepository cartRepo;
    
    @GetMapping(path = {"/cart"})
    public String getCart(@RequestParam String user, Model model) {

        if (isNull(user)) {
            user = "ANON";
        }

        // Retrieve cart
        Optional<Cart> c = cartRepo.get(user);
        List<Item> contents;
        Cart cart;

        if (c.isEmpty()) {

            cart = new Cart(user);
            System.out.printf(">>> New Cart: %s\n", cart);
            contents = cart.getContents();
        } else {
            cart = c.get();
            System.out.printf(">>> Loaded Cart: %s\n", cart);
            // List of items
            contents = cart.getContents();
        }
        
        System.out.printf(">>> Contents: %s\n", contents);

        model.addAttribute("name", user.toUpperCase());
        model.addAttribute("contents",  contents);
        
        return "cart";
    }

   @PostMapping(path = "/cart")
    public String postCart(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String name = form.getFirst("name");
        Optional<Cart> cart = cartRepo.get(name);
        Cart c = new Cart(cart.get().getName());
        
        // List of items in String
        String contents = form.getFirst("contents");
        System.out.printf(">>> Contents: %s\n\n", contents);


        String item = form.getFirst("item");
        System.out.printf(">>> Item: %s\n", item);
        if (!isNull(item)) {
            c.add(Item.create(item));
        }

        // System.out.println("cart after: " + cart);
        // System.out.println(">> serialize: " + cSvc.serialise(cart));

        // model.addAttribute("contents", cSvc.serialise(cart));
        model.addAttribute("cart", cart);
        model.addAttribute("name", name);
        model.addAttribute("user", name);
        return "cart";
    }

    private boolean isNull(String s) {
        return ((s == null) || s.trim().length() <= 0);
    }
}
