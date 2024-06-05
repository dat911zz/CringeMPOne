package com.ltdd.cringempone.ui.top100;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ltdd.cringempone.R;
import com.ltdd.cringempone.api.BaseAPIService;
import com.ltdd.cringempone.data.dto.ItemDTO;
import com.ltdd.cringempone.data.dto.TopDTO;
import com.ltdd.cringempone.databinding.Top100ChildrenItemBinding;
import com.ltdd.cringempone.service.LocalStorageService;
import com.ltdd.cringempone.ui.musicplayer.adapter.ParentItemAdapter;
import com.ltdd.cringempone.ui.musicplayer.model.ChildItem;
import com.ltdd.cringempone.ui.musicplayer.model.ParentItem;
import com.ltdd.cringempone.utils.CoreHelper;
import com.ltdd.cringempone.databinding.FragmentTop100Binding;
import com.ltdd.cringempone.utils.CustomsDialog;

import java.util.ArrayList;
import java.util.List;

public class Top100Fragment extends Fragment {
    private ArrayList<TopDTO> top100s;
    public static Top100Fragment newInstance() {
        return new Top100Fragment();
    }
    private FragmentTop100Binding binding;
    private Top100ChildrenItemBinding childrenItemBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTop100Binding.inflate(inflater, container, false);
        RecyclerView parentRecycleViewItem = binding.getRoot().findViewById(R.id.top100_parent_recyclerview);
        if (LocalStorageService.getInstance().getString("isConnect").equals("true")){
            String res = LocalStorageService.getInstance().getString("top100s");
            if (res.contains("error") || res.equals("")){
                res = BaseAPIService.getInstance().getRequest("top100");
                top100s = BaseAPIService.getInstance().getTop100List(res);
                LocalStorageService.getInstance().putString("top100s", res);
            }
            else{
                top100s = BaseAPIService.getInstance().getTop100List(res);
            }
            if(top100s != null){
                LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
                ParentItemAdapter parentItemAdapter = new ParentItemAdapter(ParentItemList(top100s));

                parentRecycleViewItem.setAdapter(parentItemAdapter);
                parentRecycleViewItem.setLayoutManager(layoutManager);
            }
        }
        else{
            CustomsDialog.showAlertDialog(getContext(), "Lỗi", "Vui lòng kiểm tra kết nối mạng!", R.drawable.baseline_error_24);
        }
        return binding.getRoot();
    }
    private List<ParentItem> ParentItemList(ArrayList<TopDTO> top100s) {
        List<ParentItem> itemList = new ArrayList<>();
        top100s.forEach((x) ->{
            ParentItem item = new ParentItem(
                    x.title,
                    ChildItemList(x.items));
            itemList.add(item);
        });
        return itemList;
    }

    // Method to pass the arguments
    // for the elements
    // of child RecyclerView
    private List<ChildItem> ChildItemList(ArrayList<ItemDTO> top100)
    {
        List<ChildItem> ChildItemList = new ArrayList<>();
        top100.forEach((x) ->{
            ChildItemList.add(new ChildItem(x.encodeId, x.title, x.thumbnail));
        });
        return ChildItemList;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CustomsDialog.hideDialog();
    }

}