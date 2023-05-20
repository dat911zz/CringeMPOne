package com.ltdd.cringempone.ui.slideshow;

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

        import java.util.ArrayList;
        import java.util.List;

public class SlideshowFragment extends Fragment {
    private ArrayList<TopDTO> top100s;
    public static com.ltdd.cringempone.ui.home.HomeFragment newInstance() {
        return new com.ltdd.cringempone.ui.home.HomeFragment();
    }
    private FragmentTop100Binding binding;
    private Top100ChildrenItemBinding childrenItemBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTop100Binding.inflate(inflater, container, false);
        RecyclerView parentRecycleViewItem = binding.getRoot().findViewById(R.id.top100_parent_recyclerview);
        if (LocalStorageService.getInstance().getString("top100s").contains("error") || LocalStorageService.getInstance().getString("top100s").equals("")){
            top100s = BaseAPIService.getInstance().getTop100List(BaseAPIService.getInstance().getRequest("top100"));
        }
        else{
            top100s = BaseAPIService.getInstance().getTop100List(LocalStorageService.getInstance().getString("top100s"));
        }
        if(top100s != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
            ParentItemAdapter parentItemAdapter = new ParentItemAdapter(ParentItemList(top100s));

            parentRecycleViewItem.setAdapter(parentItemAdapter);
            parentRecycleViewItem.setLayoutManager(layoutManager);
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
        CoreHelper.CustomsDialog.hideDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}