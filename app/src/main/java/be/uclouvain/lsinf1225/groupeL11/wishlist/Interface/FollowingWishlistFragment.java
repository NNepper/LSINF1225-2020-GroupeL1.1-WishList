package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.FollowingWishListItemAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowingWishlistFragment extends Fragment {

    private RecyclerView followingWishlistRecyclerView;
    private FollowingWishListItemAdapter followingWishListItemAdapter;
    private RecyclerView.LayoutManager followingWishlistLayoutManager;
    private TextView followingUsername;

    private User followingUser;

    private ImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        // TODO find a place to show the username of the followed user
        final Bundle bundle = this.getArguments();
        UserDAO userDAO = new UserDAO(getContext());
        WishListDAO wishListDAO = new WishListDAO(getContext());
        final ArrayList<WishList> wishLists = wishListDAO.readWishLists(bundle.getInt("followingUserID"));

        final  View view = inflater.inflate(R.layout.fragment_following_wishlist, container, false);

        followingUsername = view.findViewById(R.id.following_username);
        followingUsername.setText(bundle.getString("followingUsername"));

        followingWishlistRecyclerView = view.findViewById(R.id.following_wishlist_recycler_view);
        followingWishlistRecyclerView.setHasFixedSize(true);
        followingWishlistLayoutManager = new LinearLayoutManager(view.getContext());
        followingWishListItemAdapter = new FollowingWishListItemAdapter(wishLists);

        followingWishlistRecyclerView.setLayoutManager(followingWishlistLayoutManager);
        followingWishlistRecyclerView.setAdapter(followingWishListItemAdapter);

        followingWishListItemAdapter.setOnItemClickListener(new FollowingWishListItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // TODO Show items of following user's wishlists items
                WishList wishListToShow = wishLists.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("wishlistName", wishListToShow.name);
                bundle.putInt("wishlistID", wishListToShow.getId());

                Fragment followWishlistItemsFragment = new FollowWishlistItemsFragment();
                followWishlistItemsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, followWishlistItemsFragment).commit();
            }
        });

        this.profilePic = view.findViewById(R.id.following_profil_pic);
        profilePic.setImageResource(R.drawable.ic_default_image_profile); // TODO change into real user profile pic when implemented in DB

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment followUserProfile = new FollowProfile();
                followUserProfile.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,followUserProfile).commit(); //display the clicked fragment
            }
        });

        return view;
    }
}
