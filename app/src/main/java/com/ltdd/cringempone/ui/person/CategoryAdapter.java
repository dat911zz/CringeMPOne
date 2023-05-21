package com.ltdd.cringempone.ui.person;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoreViewHolder> {

    private List<Category> mListCategory;
    void setData(List<Category> list) {
        this.mListCategory = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoreViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category==null){
            return;
        }
        holder.imgCategory.setImageResource(category.getResouceId());
        holder.tvTitle.setText(category.getTitle());
        return;
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null) {
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView tvTitle;

        public CategoreViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.img_category);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
