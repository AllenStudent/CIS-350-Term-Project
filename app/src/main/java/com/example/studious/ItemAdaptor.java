package com.example.studious;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * responsible for managing the SQLite database
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */
public class ItemAdaptor extends RecyclerView.Adapter<ItemViewHolder> {
    /** data the recycler is displaying **/
    private ArrayList<Items> listItems;
    /** Layout Inflater **/
    private LayoutInflater inflater;
    /** device context **/
    private Context context;

    /**
     * constructor of ItemAdapter
     * @param context device context
     * @param listItems Data to initialize adapter to.
     */
    ItemAdaptor(Context context, ArrayList<Items> listItems) {
        this.listItems = listItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * create new view holder. invoked by layout manager. requested by
     *     recycler view.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate custom layout
        View view = inflater.inflate(R.layout.items_list, parent, false);

        // new view holder instance
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    /**
     * recycler wants to display data at position.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // get data based on position
        Items item = listItems.get(position);

        // update view from data
        holder.tvType.setText(String.valueOf(item.getType()));
        holder.tvTitle.setText(item.getItemTitle());
        /* store the primary key value for this item. This make a lot of
        things later much much easier. */
        holder.itemView.setTag(item.getId());

        /* load the drawable file and set for this item.
        * We might be able to load these just once in the constructor. */
        Drawable myDrawable;
        switch (item.getType())
        {
            case MainActivity.TYPE_ALARM:
                myDrawable = this.context.getResources().getDrawable(
                        R.drawable.ic_outline_access_alarm_24,
                        this.context.getTheme()
                );
                break;
            case MainActivity.TYPE_CALENDER:
                myDrawable = this.context.getResources().getDrawable(
                        R.drawable.ic_outline_calendar_month_24,
                        this.context.getTheme()
                );
                break;
            case MainActivity.TYPE_REMINDER:
                myDrawable = this.context.getResources().getDrawable(
                        R.drawable.ic_outline_circle_notifications_24,
                        this.context.getTheme()
                );
                break;
            case MainActivity.TYPE_TODO:
                myDrawable = this.context.getResources().getDrawable(
                        R.drawable.ic_outline_check_circle_24,
                        this.context.getTheme()
                );
                break;
            default:
                myDrawable = this.context.getResources().getDrawable(
                        R.drawable.ic_outline_add_24,
                        this.context.getTheme()
                );
                break;

        }
        /* set the image. duh. */
        holder.ivIcon.setImageDrawable(myDrawable);
    }

    /**
     * Get the number of items in the list.
     * @return number of items.
     */
    @Override
    public int getItemCount() {
        return listItems.size();
    }


    /* my function. makes it easier when adding and deleting. */
    /**
     * Update ItemAdapter to new data.
     * @param listItems an ArrayList of Items.
     */
    public void updateDataset(ArrayList<Items> listItems) {
        this.listItems = listItems;

        /* blunt hammer to notify data has changed */
        notifyDataSetChanged();

        /*
        Most of these seem to be based around items being added, move,
        delete, or modified in our bound data (listItems). Not the
        underlying data for listItems has changed.

        In 'theory' we could perform two separate operation. Modify the Db.
        Then manually modify listItems and then call the one of the following
        as appropriate.

        notifyItemChanged()
        notifyItemInserted()
        notifyItemMoved()
        notifyItemRangeChanged()
        notifyItemRangeInserted()
        notifyItemRangeRemoved()
        notifyItemRemoved()

        Nothing says the order in our bound data (listItems) has to be
        the same data or the same order as the db.

        For example, we could only read alerts into listItems and then
        randomize them. We can also then 'delete' something from listItems
        and are not required to delete it from the db.

        Same goes for RecyclerView. We can remove an item from there
        and not remove it from listItems. The three sections are only
        as couple as we want them.

        */
    }
}
