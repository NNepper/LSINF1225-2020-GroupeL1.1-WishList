package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.FollowListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowsFragment extends Fragment {

    private RecyclerView followListRecyclerView;
    private FollowListAdapter followListAdapter;
    private RecyclerView.LayoutManager followListLayoutManager;

    private void generateFollowList(ArrayList<User> friendList, int followNbr){
        // remove this method when DB connection is fixed and use real user's friends
        for (int i=0;i<followNbr;i++){
            User friend = new User("mailOfFriend","Follow "+ i, "1234");
            friendList.add(friend);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ArrayList<User> followList = new ArrayList<User>();
        generateFollowList(followList, 10); // remove this when DB connection is fixed

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
                // TODO show follow profile
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

        return view;
    }

}
