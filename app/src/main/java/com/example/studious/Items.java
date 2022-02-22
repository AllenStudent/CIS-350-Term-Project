package com.example.studious;

import androidx.annotation.Nullable;

/**
 * Model of data items in database.
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */
public class Items {
    /** row id in the database **/
    private int id;
    /** title of item in database. */
    private String itemTitle;
    /** item type **/
    private int type;

    /**
     * Constructor of Items clase
     * @param id datavase row id for item. -1 is fine for item to be added
     *           to database.
     * @param itemTitle Title or descrtiption of item.
     * @param type Type of item to be added. See MainActivity for item
     *             types.
     */
    public Items(int id, String itemTitle, int type) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.type = type;
    }

    /**
     * Compare two Items instances for equality.
     * Note: id is ignored.
     * @param obj item to campare to.
     * @return ture if items are equal. false if they are not.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        Items o = (Items) obj;

        return o.itemTitle.equals(this.itemTitle)
                && o.type == this.type;
    }

    /**
     * Get the row item of an item.
     * @return row id of item.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the row id of an item.
     * @param id number to set row id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the title or description of item.
     * @return title
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * Set the title or description of item.
     * @param itemTitle string to set title to.
     */
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /**
     * Get the type value of item.
     * @return item type
     */
    public int getType() {
        return type;
    }

    /**
     * set the item type.
     * @param type integer the set the item type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * String representation of item
     * @return representation of item.
     */
    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemTitle='" + itemTitle + '\'' +
                ", type=" + type +
                '}';
    }
}
