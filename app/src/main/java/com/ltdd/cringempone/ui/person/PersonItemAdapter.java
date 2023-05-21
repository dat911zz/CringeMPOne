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

public class PersonItemAdapter extends RecyclerView.Adapter<PersonItemAdapter.PersonViewHolder> {

    private List<PersonItem> personItems;

    public void setData(List<PersonItem> list) {
        this.personItems = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_person,parent,false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonItem personItem = personItems.get(position);
        if (personItem == null) {
            return;
        }

        holder.imageView.setImageResource(personItem.getResouceId());
        holder.textView.setText(personItem.getTitle());
    }

    @Override
    public int getItemCount() {
        if (personItems != null) {
            return personItems.size();
        }
        return 0;
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_person);
            textView = itemView.findViewById(R.id.img_personText);

        }
    }
}
