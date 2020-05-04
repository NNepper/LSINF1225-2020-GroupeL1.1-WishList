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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final WishListDAO wishListDAO = new WishListDAO(getActivity().getApplicationContext());

        final User mainUser = ((HomeActivity) getActivity()).mainUser;
        mainUser.wishlists = wishListDAO.readWishLists(mainUser.id);

        final View view = inflater.inflate(R.layout.fragment_home_wishlists, container, false);
        final ListView wishListView = view.findViewById(R.id.wishlist_list_view);

        this.addWishlistButton = (FloatingActionButton) view.findViewById(R.id.addWishlistButton) ;

        this.addWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add a new wishlist");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_wishlist, (ViewGroup) getView(), false);
                final EditText wishlistName = (EditText) viewInflated.findViewById(R.id.new_wishlist_name);
                builder.setView(viewInflated)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WishList newWishList = new WishList(wishlistName.getText().toString()); //create new wishlist with the given name
                                if(wishListDAO.create(newWishList, mainUser)){
                                    mainUser.wishlists.add(newWishList);
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

        wishListView.setAdapter(new WishListItemAdapter(
                this.getActivity().getApplicationContext(),
                R.layout.adapter_wishlist_item,
                mainUser.wishlists
        ));
        return view;
    }
}
