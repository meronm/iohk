package com.example.mery.maed;

/**
 * Created by mery on 12/20/2018.
 */

public class product_list {

    private String itemName;
    private String itemPrice;
    private String type;
    private String id;

    public String getType() {
        return type;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
