package vttp.shoppingcart.model;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Item {
    
    private String name;
    private int quantity;


    public String getName() {   return name;    }
    public void setName(String name) {  this.name = name;   }
    
    public int getQuantity() {  return quantity;    }
    public void setQuantity(int quantity) { this.quantity = quantity;   }

    // Create Item from json
    public static Item create(JsonObject jo) {
        Item i = new Item();
        i.setName(jo.getString("name"));
        i.setQuantity(jo.getInt("quantity"));
        return i;
    }

    // Create Item from string
    public static Item create(String jsonStr) {
        StringReader reader = new StringReader(jsonStr);
        JsonReader r = Json.createReader(reader);
        return create(r.readObject());
    }
    
    // Convert to Item into Json format
    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add("name", name)
                    .add("quantity", quantity)
                    .build();

    }
}
