package vttp.shoppingcart.model;

import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Cart {
    
    private String name;
    private List<Item> contents = new LinkedList<>();

    public Cart(String name) { }

    // Getters and setters
    public String getName() {   return name;    }

    public List<Item> getContents() {   return contents;    }
    public void setContents(List<Item> contents) {  this.contents = contents;    }

    public void add(Item i) {
        String n = i.getName().toLowerCase();
        // Check if duplicate content exists
        List<Item> sameItem = contents.stream()
                                .filter(v -> i.getName().toLowerCase().equals(n))
                                .limit(1)
                                .toList();
        if (sameItem.size() > 0) {
            Item item = sameItem.get(0);
            i.setQuantity(i.getQuantity() + item.getQuantity());
        } else {
            contents.add(i);
        }
    }

    public JsonObject toJson() {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        contents.stream()
                // convert each item(name + quan) into json format
                .map(i -> i.toJson())
                // array of json items
                .forEach(i -> arr.add(i));
        return Json.createObjectBuilder()
                    .add("name", name)
                    .add("contents", arr)
                    .build();
    }

    public static Cart create(String jsonStr) {
        Reader reader = new StringReader(jsonStr);
        JsonReader jr = Json.createReader(reader);
        JsonObject c = jr.readObject();
        // Why .getString?
        Cart cart = new Cart(c.getString("name"));
        List<Item> contents = c.getJsonArray("contents").stream()
                            .map(v -> (JsonObject) v )
                            .map(Item :: create)
                            .toList();
        cart.setContents(contents);
        return cart;
    }

}
