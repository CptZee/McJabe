package com.github.cptzee.mcjabe;

public class Items {

    private String item_name;
    private String item_description;
    private int item_image;

    public Items(String item_name, String item_description, int item_image) {
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_image = item_image;
    }

    private void setItemName(String item_name) {
        this.item_name = item_name;
    }

    private String getItemName() {
        return item_name;
    }

    private void setItemDescription(String item_description) {
        this.item_description = item_description;
    }

    private String getItemDescription() {
        return item_description;
    }

}
