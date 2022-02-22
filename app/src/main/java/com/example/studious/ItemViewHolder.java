package com.example.studious;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom view holder.
 * supposedly this exists to offload a bunch of overhead, more efficient,
 * bring world peace, or something. (Legacy) ListView was simpler but
 * much less functional and had a much higher overhead.
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    /** Textview for title in items_list activity. **/
    public final TextView tvTitle;

    /** Textview for item type in items_list activity. **/
    public final TextView tvType;

    /** Textview for icon in items_list activity. **/
    public final ImageView ivIcon;

    /**
     * Initialize ItemViewHolder
     *
     * @param itemView activity view
     */
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        ivIcon = itemView.findViewById(R.id.iv_icon);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvType = itemView.findViewById(R.id.tv_type);
    }
}
