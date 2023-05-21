package com.ltdd.cringempone.ui.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.ltdd.cringempone.R;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        rcvCategory = findViewById(R.id.rcv_category);
        categoryAdapter = new CategoryAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCategory.setLayoutManager(linearLayoutManager);

        categoryAdapter.setData(getListCategory());
        rcvCategory.setAdapter(categoryAdapter);
    }
    private List<CategoryPersonItem> getListCategory() {
        List<CategoryPersonItem> listCategory = new ArrayList<>();

        List<PersonItem> listPerson = new ArrayList<>();
        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 1"));
        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 2"));
        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 3"));

        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 1"));
        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 2"));
        listPerson.add(new PersonItem(R.drawable.baseline_favorite_24, "Book 3"));

        listCategory.add(new CategoryPersonItem("Ca Nhan", listPerson));
        listCategory.add(new CategoryPersonItem("Ca Nhan2", listPerson));
        listCategory.add(new CategoryPersonItem("Ca Nhan3", listPerson));
        return listCategory;
    }
}