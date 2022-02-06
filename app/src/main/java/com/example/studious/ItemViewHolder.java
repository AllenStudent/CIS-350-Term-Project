package com.example.studious;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* custom view holder. */
public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView tvTitle;
    TextView tvType;
    ImageView ivIcon;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        ivIcon = itemView.findViewById(R.id.iv_icon);
        tvTitle =  itemView.findViewById(R.id.tv_title);
        tvType = itemView.findViewById(R.id.tv_type);
    }


}
