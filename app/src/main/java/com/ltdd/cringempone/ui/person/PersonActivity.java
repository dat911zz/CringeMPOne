package com.ltdd.cringempone.ui.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private RecyclerView rcvCategory;
    private RecyclerView rcvUser;
    private CategoryAdapter categoryAdapter;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.baseline_view_headline_24);
        getSupportActionBar().setTitle(" Cá Nhân");


        rcvCategory = findViewById(R.id.rcv_category);
        rcvUser = findViewById(R.id.rcv_user);

        categoryAdapter = new CategoryAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvCategory.setLayoutManager(gridLayoutManager);
        rcvCategory.setFocusable(false);
        rcvCategory.setNestedScrollingEnabled(false);

        categoryAdapter.setData(getListCateGory());
        rcvCategory.setAdapter(categoryAdapter);


        userAdapter = new UserAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.setFocusable(false);
        rcvUser.setNestedScrollingEnabled(false);

        userAdapter.setData(getListUser());
        rcvUser.setAdapter(userAdapter);
    }

    private List<Category> getListCateGory() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(R.drawable.ic_favorite_24, "Bài hát yêu thích"));
        list.add(new Category(R.drawable.baseline_arrow_circle_down_24, "Đã tải"));
        list.add(new Category(R.drawable.baseline_person_add_alt_1_24, "Nghệ sĩ"));
        list.add(new Category(R.drawable.baseline_podcasts_24, "Podcast"));

        list.add(new Category(R.drawable.baseline_upload_24, "Upload"));
        list.add(new Category(R.drawable.baseline_live_tv_24, "MV"));
        list.add(new Category(R.drawable.baseline_access_time_24, "Nghe gần đây"));
        list.add(new Category(R.drawable.baseline_album_24, "Album"));

        return list;
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        list.add(new User(R.drawable.baseline_add_24, "Tạo playlist"));
        list.add(new User(R.drawable.img_1, "Những bài hát hay nhất"));
        list.add(new User(R.drawable.img_3, "Lofi một chút thôi"));
        list.add(new User(R.drawable.img_2, "Có thể bạn thích"));
        return list;
    }
}