package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

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
                        UserDAO userDAO = new UserDAO(getContext());
                        userDAO.unfollow(mainUser, followList.get(position));
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
            final EditText query = (EditText) viewInflated.findViewById(R.id.query);
            builder.setView(viewInflated)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((HomeActivity) getActivity()).searchUsersResult = doMySearch(query.getText().toString());
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, new SearchUsersResultFragment()).commit(); //display the clicked fragment
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

    @SuppressLint("SetTextI18n")
    public ArrayList<User> doMySearch(String query) {
        Log.d("query", query);
        UserDAO userDAO = new UserDAO(getContext());
        ArrayList<User> users = userDAO.getFollowable(mainUser.id);
        ArrayList<User> filtered = new ArrayList<>();
        if (users == null) {
            Log.d("User", "No users followable");
            return filtered;
        }
        for (User user : users) {
            Log.d("User", user.username);
            if (user.username.contains(query)) {
                filtered.add(user);
            }
        }
        return filtered;
    }

}
