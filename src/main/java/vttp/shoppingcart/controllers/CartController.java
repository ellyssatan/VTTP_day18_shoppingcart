package vttp.shoppingcart.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.shoppingcart.services.CartService;

@Controller
@RequestMapping(path = {"", "/"})
public class CartController {

    @Autowired
    private CartService cSvc;
    
    @GetMapping(path = {"/cart"})
    public String getCart(@RequestParam String user, Model model) {

        if (isNull(user)) {
            user = "ANON";
        }

        model.addAttribute("name", user.toUpperCase());
        return "cart";
    }

   @PostMapping(path = {"/cart"})
    public String postCart(@RequestBody MultiValueMap<String, String> form, Model model, HttpSession sess) {
        
        List<String> cart = new LinkedList<>();

        String name = form.getFirst("name");
        
        if (!isNull(name)) {
			// new session
			System.out.println("name not in session");
			sess.setAttribute("name", name);
			// cart = new LinkedList<>();
			sess.setAttribute("cart", cart);

		} 

		name = (String) sess.getAttribute("name");
		cart = (List<String>) sess.getAttribute("cart");
		String item = form.getFirst("item");
		if (!isNull(item))
			cart.add(item);


        model.addAttribute("cart", cart);
        model.addAttribute("name", name);
        return "cart";
    }

    private boolean isNull(String s) {
        return ((s == null) || s.trim().length() <= 0);
    }
}
