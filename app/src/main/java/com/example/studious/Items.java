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
    /** Item start time. **/
    private String itemStartTime;
    /** Item end time. **/
    private String itemEndTime;
    /** Item start date. **/
    private String itemStartDate;
    /** Item end date. **/
    private String itemEndDate;

    /**
     * Constructor of the Items class.
     *
     * @param id        database row id for item. -1 is fine for item to be added
     *                  to database.
     * @param itemTitle Title or description of item.
     * @param type      Type of item to be added. See MainActivity for item
     *                  types.
     */
    public Items(int id, String itemTitle, int type, String itemNotes,
                 String itemStartTime,
                 String itemEndTime,
                 String itemStartDate,
                 String itemEndDate) {
        this.id = id;
        this.itemTitle = itemTitle;
        this.type = type;
        this.itemNotes = itemNotes;
        this.itemStartTime = itemStartTime;
        this.itemEndTime = itemEndTime;
        this.itemStartDate = itemStartDate;
        this.itemEndDate = itemEndDate;

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

        /** Note: does not compare Notes and Duration, as they're irrelevant to showing it is the same exact item. **/
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
     * Set the start time of item.
     *
     * @param itemStartTime set start date to.
     */
    public void setItemStartTime(String itemStartTime) {
        this.itemStartTime = itemStartTime;
    }

    /**
     * Get the start time of item.
     *
     * @return itemStartTime
     */
    public String getItemStartTime() {
        return itemStartTime;
    }
    /**
     * Set the start date of item.
     *
     * @param itemStartDate set start date to.
     */
    public void setItemStartDate(String itemStartDate) {
        this.itemStartDate = itemStartDate;
    }

    /**
     * Get the start date of item.
     *
     * @return itemStartDate
     */
    public String getItemStartDate() {
        return itemStartDate;
    }

    /**
     * Set the end time of item.
     *
     * @param itemEndTime set end time to.
     */
    public void setItemEndTime(String itemEndTime) {
        this.itemEndTime = itemEndTime;
    }

    /**
     * Get the end time of item.
     *
     * @return itemEndTime
     */
    public String getItemEndTime() {
        return itemEndTime;
    }
    /**
     * Set the end date of item.
     *
     * @param itemEndDate set end date to.
     */
    public void setItemEndDate(String itemEndDate) {
        this.itemEndDate = itemEndDate;
    }

    /**
     * Get the end date of item.
     *
     * @return itemEndDate
     */
    public String getItemEndDate() {
        return itemEndDate;
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
                ", itemTitle='" + itemTitle + '\'' +
                ", type=" + type +
                ", itemNotes='" + itemNotes + '\'' +
                ", itemStartTime='" + itemStartTime + '\'' +
                ", itemEndTime='" + itemEndTime + '\'' +
                ", itemStartDate='" + itemStartDate + '\'' +
                ", itemEndDate='" + itemEndDate + '\'' +
                '}';
    }
}
