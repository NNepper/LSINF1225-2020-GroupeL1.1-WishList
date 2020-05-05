package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.FollowDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.SearchUsersResultAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class FollowRequestFragment extends Fragment {

    private RecyclerView followRequestListRecyclerView;
    private SearchUsersResultAdapter followRequestListAdapter;
    private RecyclerView.LayoutManager followRequestListLayoutManager;
    private User mainUser;
    private TextView disableNoMatch;
    private ImageView backArrow;


    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        FollowDAO followDAO = new FollowDAO(getContext());
        final ArrayList<User> followRequest = followDAO.getPending(mainUser);
        final View view = inflater.inflate(R.layout., container, false);
        this.backArrow = (ImageView) view.findViewById(R.id.);

        followRequestListRecyclerView = view.findViewById(R.id.);
        followRequestListRecyclerView.setHasFixedSize(true);
        followRequestListLayoutManager = new LinearLayoutManager(view.getContext());
        followRequestListAdapter = new SearchUsersResultAdapter(searchUsersResultsList);

        if (searchUsersResultsList.size() != 0) {
            this.disableNoMatch= view.findViewById(R.id.noResultMatched);
            Log.d("disableNoMatch", disableNoMatch.toString());
            this.disableNoMatch.setText("");
        } else {
            this.disableNoMatch= view.findViewById(R.id.noResultMatched);
            Log.d("disableNoMatch", disableNoMatch.toString());
            this.disableNoMatch.setText("No result matched your search");
        }

        this.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Status", "Clicked !!");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FollowsFragment()).commit();
            }
        });

        followRequestListRecyclerView.setLayoutManager(followRequestListLayoutManager);
        followRequestListRecyclerView.setAdapter(followRequestListAdapter);

        followRequestListAdapter.setOnItemClickLister(new SearchUsersResultAdapter.onItemClickListener() { // set the on click listener for the card inside the recycler view
            @Override
            public void onItemClick(int position) {
                // TODO show follow profile ?
            }

            @Override
            public void onAddClick(final int position) {
                String message;
                if (searchUsersResultsList.get(position).privacy == 0) {
                    message = "Do you want to follow " + searchUsersResultsList.get(position).username + "?";
                } else {
                    message = searchUsersResultsList.get(position).username + " account's is private.\nDo you want to send a follow request ?";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("New follow");
                builder.setMessage(message);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FollowDAO followDAO = new FollowDAO(getContext());
                        followDAO.addFollow(mainUser, searchUsersResultsList.get(position));
                        searchUsersResultsList.remove(position);
                        followRequestListAdapter.notifyItemRemoved(position);
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
