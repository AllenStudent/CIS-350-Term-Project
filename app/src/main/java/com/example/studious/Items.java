package com.example.studious;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Model of data items in database.
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */

//TODO: Item Time/Date Variable; Link Todo/Alarm/Reminder Items To Calendar Parent Item
public class Items {
    /** Calendar Type **/
    public static final int TYPE_CALENDAR = 0;
    /** To Do Type **/
    public static final int TYPE_TODO = 1;
    /** Reminder Type **/
    public static final int TYPE_REMINDER = 2;
    /** Alarm Type **/
    public static final int TYPE_ALARM = 3;


    /** row id in the database **/
    private int id;
    /** title of item in database. **/
    private String itemTitle;
    /** item type **/
    private int type;
    /**item notes **/
    private String itemNotes;
    /** Item start datetime. **/
    private String itemStart;
    /** Item end datetime. **/
    private String itemEnd;

    /**
     * Constructor of the Items class.
     *
     * @param id        database row id for item. -1 is fine for item to be added
     *                  to database.
     * @param itemTitle Title or description of item.
     * @param type      Type of item to be added. See MainActivity for item
     *                  types.
     */
    public Items(int id, String itemTitle, int type, String itemNotes, String itemStart, String itemEnd) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.type = type;
        this.itemNotes = itemNotes;
        this.itemStart = itemStart;
        this.itemEnd = itemEnd;

    }

    /**
     * Compare two Items instances for equality.
     * Note: id is ignored.
     *
     * @param obj item to compare to.
     * @return ture if items are equal. false if they are not.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (getClass() != obj.getClass())
            return false;

        Items o = (Items) obj;

        /** Note: does not compare Notes and Duration, as they're irrelevent to showing it is the same exact item. **/
        return o.itemTitle.equals(this.itemTitle)
                && o.type == this.type;
    }

    /**
     * Get the row item of an item.
     *
     * @return row id of item.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the row id of an item.
     *
     * @param id number to set row id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the title or description of item.
     *
     * @return title
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * Set the title or description of item.
     *
     * @param itemTitle string to set title to.
     */
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /**
     * Set the notes of item.
     *
     * @param itemNotes set notes to.
     */
    public void setItemNotes(String itemNotes) {
        this.itemNotes = itemNotes;
    }

    /**
     * Get the notes of item.
     *
     * @return itemNotes
     */
    public String getItemNotes () {
        return itemNotes;
    }

    /**
     * Set the start date of item.
     *
     * @param itemStart set start date to.
     */
    public void setItemStart(String itemStart) {
        this.itemStart = itemStart;
    }

    /**
     * Get the start date of item.
     *
     * @return itemStart
     */
    public String getItemStart () {
        return itemStart;
    }

    /**
     * Set the end date of item.
     *
     * @param itemEnd set end date to.
     */
    public void setItemEnd(String itemEnd) {
        this.itemEnd = itemEnd;
    }

    /**
     * Get the end date of item.
     *
     * @return itemEnd
     */
    public String getItemEnd () {
        return itemEnd;
    }

    /**
     * Get the type value of item.
     *
     * @return item type
     */
    public int getType() {
        return type;
    }

    /**
     * set the item type.
     *
     * @param type integer the set the item type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * String representation of item
     *
     * @return representation of item.
     */
    @Override
    @NonNull
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", itemTitle=" + itemTitle + '\'' +
                ", type=" + type +
                ", itemNotes=" + itemNotes + '\'' +
                ", itemStart=" + itemStart + '\'' +
                ", itemEnd=" + itemEnd + '\'' +
                '}';
    }
}
