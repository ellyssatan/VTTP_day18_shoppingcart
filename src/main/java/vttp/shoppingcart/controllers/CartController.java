package vttp.shoppingcart.controllers;

import java.util.LinkedList;
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
import vttp.shoppingcart.services.CartService;

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
        Cart cart = (Cart) c.stream().toList();

        List<Item> contents = cart.getContents();

        System.out.printf(">>> Cart: %s", cart);

        model.addAttribute("name", user.toUpperCase());
        model.addAttribute("cart",  contents);
        
        return "cart";
    }

   @PostMapping(path = {"/cart"})
    public String postCart(@RequestBody MultiValueMap<String, String> form, Model model) {
        
        String name = form.getFirst("name");
        
        // Cart cart = new Cart(name);
        // cart.getContents();

        // String c = form.getFirst("contents");
        // System.out.printf(">>> contents: %s", c);
        // if (!isNull(c)) {
        //     cart = cSvc.deserialise(c);
        // }
        // System.out.println(cart);


        // String item = form.getFirst("item");
        // System.out.printf(">>> item: %s", item);
        // if (!isNull(item)) {
        //     cart.add(item);
        // }

        // System.out.println("cart after: " + cart);
        // System.out.println(">> serialize: " + cSvc.serialise(cart));

        // model.addAttribute("contents", cSvc.serialise(cart));
        // model.addAttribute("cart", cart);
        // model.addAttribute("name", name);
        // model.addAttribute("user", name);
        return "cart";
    }

    private boolean isNull(String s) {
        return ((s == null) || s.trim().length() <= 0);
    }
}
