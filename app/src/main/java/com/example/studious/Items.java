package com.example.studious;

/**
 * Model of data items in database.
 */
public class Items {
    private int id;
    private String itemTitle;
    private int type;

    public Items(int id, String itemTitle, int type) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.type = type;
    }

    public Items() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemTitle='" + itemTitle + '\'' +
                ", type=" + type +
                '}';
    }
}