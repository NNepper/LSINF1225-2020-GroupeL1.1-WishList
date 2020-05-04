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
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.SearchUsersResultAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class SearchUsersResultFragment extends Fragment {

    private RecyclerView searchUsersResultsListRecyclerView;
    private SearchUsersResultAdapter searchUsersResultsListAdapter;
    private RecyclerView.LayoutManager searchUsersResultsListLayoutManager;
    private User mainUser;
    private Bundle data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;

        // TODO récupérer les extras pour avoir la lite des comptes à afficher

        final ArrayList<User> searchUsersResultsList = new ArrayList<>(); // TODO mettre la liste des résultats

        final View view = inflater.inflate(R.layout.fragment_search_users_result, container, false);

        searchUsersResultsListRecyclerView = view.findViewById(R.id.search_result_recycler_view);
        searchUsersResultsListRecyclerView.setHasFixedSize(true);
        searchUsersResultsListLayoutManager = new LinearLayoutManager(view.getContext());
        searchUsersResultsListAdapter = new SearchUsersResultAdapter(searchUsersResultsList);

        searchUsersResultsListRecyclerView.setLayoutManager(searchUsersResultsListLayoutManager);
        searchUsersResultsListRecyclerView.setAdapter(searchUsersResultsListAdapter);

        searchUsersResultsListAdapter.setOnItemClickLister(new SearchUsersResultAdapter.onItemClickListener() { // set the on click listener for the card inside the recycler view
            @Override
            public void onItemClick(int position) {
                // TODO show follow profile ?
            }

            @Override
            public void onAddClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Unfollow");
                builder.setMessage("Are you sure you want to unfollow " + searchUsersResultsList.get(position).username + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchUsersResultsList.remove(position);
                        searchUsersResultsListAdapter.notifyItemRemoved(position);
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
