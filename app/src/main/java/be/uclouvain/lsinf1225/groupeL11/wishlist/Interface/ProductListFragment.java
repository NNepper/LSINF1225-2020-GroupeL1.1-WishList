package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.ProductListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProductListFragment extends Fragment {
    private RecyclerView productView;
    private ProductListAdapter productItemAdapter;
    private RecyclerView.LayoutManager productLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        UserDAO userDAO = new UserDAO(getContext());

        final User mainUser = ((HomeActivity) getActivity()).mainUser;

        WishListDAO wishListDAO = new WishListDAO(getContext());
        final ArrayList<WishList> wishLists = wishListDAO.readWishLists(bundle.getInt("followingUserID"));

        final  View view = inflater.inflate(R.layout.fragment_home_wishlists, container, false);

        //TODO: not finished
    }
}
