package com.ltdd.cringempone.ui.musicplayer.adapter;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.ui.musicplayer.RecyclerViewItemClickListener;
import com.ltdd.cringempone.ui.musicplayer.model.ChildItem;
import com.ltdd.cringempone.ui.playlist.PlaylistActivity;
import com.ltdd.cringempone.utils.CustomsDialog;
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
        childViewHolder.childItemTitle.setText(childItem.getTitle());
        childViewHolder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(view.getContext(),"Child Item clicked: "+ childItem.getId() ,Toast.LENGTH_LONG).show();
                Intent playlistIntent = new Intent(view.getContext(), PlaylistActivity.class);
                playlistIntent.putExtra("playlistId", childItem.getId());
                CustomsDialog.showLoadingDialog(view.getContext());
                new Handler().postDelayed(() -> view.getContext().startActivity(playlistIntent),0);
//                new Handler().postDelayed(() -> CustomsDialog.hideDialog(),2500);
            }
        });
        Picasso.get().load(childItem.getImg()).fit().into(childViewHolder.childItemImg);
    }
    @Override
    public int getItemCount() {
        return ChildItemList.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        private TextView childItemTitle;
        private ImageView childItemImg;
        private RecyclerViewItemClickListener itemClickListener;
        ChildViewHolder(View itemView) {
            super(itemView);
            childItemTitle = itemView.findViewById(R.id.top100_child_item_title);
            childItemImg = itemView.findViewById(R.id.top100_img_child_item);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        public void setItemClickListener(RecyclerViewItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAbsoluteAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAbsoluteAdapterPosition(), true);
            return true;
        }
    }
}
