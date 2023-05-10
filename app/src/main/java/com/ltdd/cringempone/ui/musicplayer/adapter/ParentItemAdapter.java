package com.ltdd.cringempone.ui.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.ui.musicplayer.RecyclerViewItemClickListener;
import com.ltdd.cringempone.ui.musicplayer.model.ParentItem;

import java.util.List;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ParentItem> itemList;
    public ParentItemAdapter(List<ParentItem> itemList){
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ParentItemAdapter.ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top100_parent_item, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentItemAdapter.ParentViewHolder holder, int position) {
        ParentItem parentItem = itemList.get(position);
        holder.ParentItemTile.setText(parentItem.getParentItemTitle());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.ChildRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setInitialPrefetchItemCount(parentItem.getChildItemList().size());
        ChildItemAdapter childItemAdapter = new ChildItemAdapter(parentItem.getChildItemList());

        holder.ChildRecyclerView.setLayoutManager(layoutManager);
        holder.ChildRecyclerView.setAdapter(childItemAdapter);
        holder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {
        private TextView ParentItemTile;
        private RecyclerView ChildRecyclerView;

        public ParentViewHolder(final View itemView){
            super(itemView);
            ParentItemTile = itemView.findViewById(R.id.top100_parent_item_title);
            ChildRecyclerView = itemView.findViewById(R.id.top100_child_recyclerview);
        }
    }
}
