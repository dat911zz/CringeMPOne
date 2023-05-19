package com.ltdd.cringempone.ui.search.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.SearchPlaylistDTO;
import com.ltdd.cringempone.data.dto.SearchSongDTO;
import com.ltdd.cringempone.service.MediaControlReceiver;
import com.ltdd.cringempone.ui.musicplayer.PlayerActivity;
import com.ltdd.cringempone.ui.musicplayer.RecyclerViewItemClickListener;
import com.ltdd.cringempone.ui.playlist.model.PlaylistItem;
import com.ltdd.cringempone.ui.search.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<PlaylistItem> playlistItemList;
    private List<SearchSongDTO> items;

    public SongAdapter(ArrayList<SearchSongDTO> items) {
        this.playlistItemList = toPlaylistItemList(items);
        this.items = items;
    }
    public List<PlaylistItem> toPlaylistItemList(ArrayList<SearchSongDTO> items){
        List<PlaylistItem> list = new ArrayList<>();
        items.forEach(i -> {
            list.add(new PlaylistItem(i.encodeId, i.title, i.artistsNames, i.thumbnailM));
        });
        return list;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_playlist_list_item,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
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
                Intent mediaIntent = new Intent(view.getContext(), PlayerActivity.class);
                mediaIntent.putExtra("currentSong", MediaControlReceiver.getInstance().getCurrentSong().encodeId);
                view.getContext().startActivity(mediaIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private ImageView img, optionBtn;
        private TextView title, artistsNames;
        private RecyclerViewItemClickListener itemClickListener;


        public SongViewHolder(@NonNull View itemView) {
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
            itemClickListener.onClick(v, getAbsoluteAdapterPosition(), true);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAbsoluteAdapterPosition(), true);
            return true;
        }
    }
}
