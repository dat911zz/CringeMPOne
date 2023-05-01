package com.ltdd.cringempone.ui.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.ui.musicplayer.model.ChildItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {

    private List<ChildItem> ChildItemList;
    // Constructor
    ChildItemAdapter(List<ChildItem> childItemList) {
        this.ChildItemList = childItemList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top100_children_item, viewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position) {
        ChildItem childItem = ChildItemList.get(position);
        childViewHolder.childItemTitle.setText(childItem.getChildItemTitle());
        Picasso.get().load(childItem.getChildItemImg()).fit().into(childViewHolder.childItemImg);
    }

    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        TextView childItemTitle;
        ImageView childItemImg;

        ChildViewHolder(View itemView) {
            super(itemView);
            childItemTitle = itemView.findViewById(R.id.top100_child_item_title);
            childItemImg = itemView.findViewById(R.id.top100_img_child_item);
        }
    }
}
