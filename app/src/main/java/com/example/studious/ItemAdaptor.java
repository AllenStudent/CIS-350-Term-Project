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


public class ItemAdaptor extends RecyclerView.Adapter<ItemViewHolder> {
    /* data the recycler is displaying */
    private ArrayList<Items> listItems;
    private LayoutInflater inflater;
    private Context context;

    ItemAdaptor(Context context, ArrayList<Items> listItems) {
        this.listItems = listItems;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // create new view. invoked by layout manager.
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate custom layout
        View view = inflater.inflate(R.layout.items_list, parent, false);

        // new view holder instance
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    /* display data at position. */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // get data based on position
        Items item = listItems.get(position);

        // update view from data
        holder.tvType.setText(String.valueOf(item.getType()));
        holder.tvTitle.setText(item.getItemTitle());
        holder.itemView.setTag(item.getId());

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
        holder.ivIcon.setImageDrawable(myDrawable);
    }

    /* number of items in list */
    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public void updateDataset(ArrayList<Items> listItems) {
        this.listItems = listItems;

        /* blunt hammer to notify data has changed */
        notifyDataSetChanged();
    }
}
