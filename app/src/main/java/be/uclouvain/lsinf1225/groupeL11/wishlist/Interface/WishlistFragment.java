package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.WishListItemAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class WishlistFragment extends Fragment {

    private FloatingActionButton addWishlistButton;
    private String newWishListName;

    private RecyclerView wishListRecyclerView;
    private WishListItemAdapter wishListAdapter;
    private RecyclerView.LayoutManager wishListLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final WishListDAO wishListDAO = new WishListDAO(getActivity().getApplicationContext());

        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        mainUser.wishlists = wishListDAO.readWishLists(mainUser.id);

        final View view = inflater.inflate(R.layout.fragment_home_wishlists, container, false);

        wishListRecyclerView = view.findViewById(R.id.wishlist_recycler_view);
        wishListRecyclerView.setHasFixedSize(true);
        wishListLayoutManager = new LinearLayoutManager(view.getContext());
        wishListAdapter = new WishListItemAdapter(mainUser.wishlists);

        wishListRecyclerView.setLayoutManager(wishListLayoutManager);
        wishListRecyclerView.setAdapter(wishListAdapter);

        wishListAdapter.setOnItemClickLister(new WishListItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WishList clickedWishList= mainUser.wishlists.get(position);
                Bundle data = new Bundle();
                data.putInt("wishListID", clickedWishList.getId());
                data.putString("wishListName", clickedWishList.name);

                Fragment productListFragment = new ProductListFragment();
                productListFragment.setArguments(data);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,productListFragment).commit(); //display the clicked fragment
            }

            @Override
            public void onDeleteClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete WishList");
                builder.setMessage("Are you sure you want to delete" + mainUser.wishlists.get(position)+ "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(wishListDAO.delete(mainUser.wishlists.get(position).getId())) {
                            mainUser.wishlists.remove(position);
                            wishListAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        this.addWishlistButton = (FloatingActionButton) view.findViewById(R.id.addWishlistButton) ;
        this.addWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add a new wishlist");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_wishlist, (ViewGroup) getView(), false);
                final EditText wishListName = (EditText) viewInflated.findViewById(R.id.new_wishlist_name);
                builder.setView(viewInflated)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WishList newWishList = new WishList(wishListName.getText().toString()); //create new wishlist with the given name
                                if(wishListDAO.create(newWishList, mainUser)){
                                    mainUser.wishlists.add(newWishList);
                                    wishListRecyclerView.getAdapter().notifyDataSetChanged();
                                }
                                else {
                                    CharSequence text = "Error while creating the wishlist";
                                    int duration = Toast.LENGTH_SHORT;

                                    Toast toast = Toast.makeText(getContext(), text, duration);
                                    toast.show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        return view;
    }
}
