package com.ltdd.cringempone.ui.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    private Context context;
    private List<CategoryPersonItem> categoryPersonItems;

    public void setData(List<CategoryPersonItem> list) {
        this.categoryPersonItems = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragmentperson,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryPersonItem categoryPersonItem = categoryPersonItems.get(position);
        if (categoryPersonItem == null) {
            return;
        }
        holder.textView.setText(categoryPersonItem.getNameCategory());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        PersonItemAdapter personItemAdapter = new PersonItemAdapter();
        personItemAdapter.setData(categoryPersonItem.getPersonItems());
        holder.recyclerView.setAdapter(personItemAdapter);
    }

    @Override
    public int getItemCount() {
        if (categoryPersonItems != null) {
            return categoryPersonItems.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private RecyclerView recyclerView;

        public CategoryViewHolder(@NonNull View itemView) {

            super(itemView);

            textView = itemView.findViewById(R.id.textview_name_itemperson);
            recyclerView = itemView.findViewById(R.id.recyle_personitem);
        }
    }
}
