package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.FollowDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.FollowListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowsFragment extends Fragment {

    private RecyclerView followListRecyclerView;
    private FollowListAdapter followListAdapter;
    private RecyclerView.LayoutManager followListLayoutManager;
    private User mainUser;
    private FloatingActionButton searchButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        FollowDAO followDAO = new FollowDAO(getContext());
        mainUser.following = followDAO.getFollowing(mainUser.id, null);
        final ArrayList<User> followList = mainUser.following;

        final View view = inflater.inflate(R.layout.fragment_home_follows, container, false);

        followListRecyclerView = view.findViewById(R.id.follow_list_recycler_view);
        followListRecyclerView.setHasFixedSize(true);
        followListLayoutManager = new LinearLayoutManager(view.getContext());
        followListAdapter = new FollowListAdapter(followList);

        followListRecyclerView.setLayoutManager(followListLayoutManager);
        followListRecyclerView.setAdapter(followListAdapter);

        followListAdapter.setOnItemClickLister(new FollowListAdapter.onItemClickListener(){ // set the on click listener for the card inside the recycler view
            @Override
            public void onItemClick(int position) {
                User followingUser = followList.get(position);
                Bundle data = new Bundle();
                data.putInt("followingUserID", followingUser.id);
                data.putString("followingUsername", followingUser.username);

                Fragment followingFragment = new FollowingWishlistFragment();
                followingFragment.setArguments(data);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,followingFragment).commit(); //display the clicked fragment
            }

            @Override
            public void onDeleteClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Unfollow");
                builder.setMessage("Are you sure you want to unfollow " + followList.get(position).username + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        followList.remove(position);
                        followListAdapter.notifyItemRemoved(position);
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


        this.searchButton = (FloatingActionButton) view.findViewById(R.id.search_button);

        this.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Search an user");
            View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_search_user, (ViewGroup) getView(), false);
            final EditText query = (EditText) viewInflated.findViewById(R.id.search_button);
            builder.setView(viewInflated)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<User> searchUsersResultList = doMySearch(query.getText().toString());
                        Intent searchUsersResult = new Intent(getContext(), SearchUsersResultFragment.class);
                        //searchUsersResult.putExtras(searchUsersResultList); // TODO Passer la liste de résultat vers la prochaine activité
                        startActivity(searchUsersResult);
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

    public ArrayList<User> doMySearch(String query) {
        UserDAO userDAO = new UserDAO(getContext());
        ArrayList<User> users = userDAO.getAllUser(mainUser.id);
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (user.username.contains(query)) {
                filtered.add(user);
            }
        }
        return filtered;
    }

}
