package com.ltdd.cringempone.ui.playlist.adapter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.PlayerActivity;
import com.ltdd.cringempone.ui.musicplayer.RecyclerViewItemClickListener;
import com.ltdd.cringempone.ui.playlist.model.PlaylistItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<PlaylistItem> playlistItemList;
    private List<ItemDTO> items;
    int lastClickedPos = 0;


    public PlaylistAdapter(ArrayList<ItemDTO> items) {
        this.items = items;
        this.playlistItemList = toPlaylistItemList(items);
    }
    public List<PlaylistItem> toPlaylistItemList(ArrayList<ItemDTO> items){
        List<PlaylistItem> list = new ArrayList<>();
        items.forEach(i -> {
            list.add(new PlaylistItem(i.encodeId, i.title, i.artistsNames, i.thumbnailM));
        });
        return list;
    }
    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_playlist_list_item, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistItem item = playlistItemList.get(position);
        holder.title.setText(item.getTitle());
        holder.artistsNames.setText(item.getArtistsNames());
        Picasso.get().load(item.getImgLink()).into(holder.img);
        holder.setItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (position != MediaControlReceiver.getInstance().getCurrentPos()){
                    MediaControlReceiver.getInstance().setCurrentPos(position);
                }
                view.getContext().startActivity(new Intent(view.getContext(), PlayerActivity.class));
            }
        });
        if (position == MediaControlReceiver.getInstance().getCurrentPos()){
            holder.title.setText("▶  " + holder.title.getText());
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return playlistItemList.size();
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private ImageView img, optionBtn;
        private TextView title, artistsNames;
        private RecyclerViewItemClickListener itemClickListener;
        PlaylistViewHolder(View itemView){
            super(itemView);
            img = itemView.findViewById(R.id.playlist_list_item_img);
            title = itemView.findViewById(R.id.playlist_list_item_title);
            artistsNames = itemView.findViewById(R.id.playlist_list_item_artistsNames);
            optionBtn = itemView.findViewById(R.id.playlist_list_item_options);

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
