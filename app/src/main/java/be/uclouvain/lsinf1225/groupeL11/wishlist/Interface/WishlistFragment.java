package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.WishListItemAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class WishlistFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] testItems = {"test", "test1", "test2"};
        View view = inflater.inflate(R.layout.fragment_home_wishlists, container, false);
        ListView wishListView = view.findViewById(R.id.wishlist_list_view);

        //TODO: replace the adapter for user's wishlist using: setAdapter(new WishListItemAdapter([...]));
        wishListView.setAdapter(new ArrayAdapter<String>(
                getContext(),
                R.layout.adapter_wishlist_item,
                R.id.item_wishlist_title,
                testItems
        ));
        return view;
    }
}
