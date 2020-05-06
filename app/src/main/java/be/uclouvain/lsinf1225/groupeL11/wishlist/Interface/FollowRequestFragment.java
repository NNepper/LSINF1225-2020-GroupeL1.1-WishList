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
        final FollowDAO[] followDAO = {new FollowDAO(getContext())};
        final ArrayList<User> followRequest = followDAO[0].getPending(mainUser);
        final View view = inflater.inflate(R.layout.fragment_follow_request, container, false);
        this.backArrow = (ImageView) view.findViewById(R.id.search_users_back_arrow);

        followRequestListRecyclerView = view.findViewById(R.id.follow_request_recycler_view);
        followRequestListRecyclerView.setHasFixedSize(true);
        followRequestListLayoutManager = new LinearLayoutManager(view.getContext());
        followRequestListAdapter = new SearchUsersResultAdapter(followRequest);

        if (followRequest.size() != 0) {
            this.disableNoMatch= view.findViewById(R.id.noRequest);
            Log.d("disableNoMatch", disableNoMatch.toString());
            this.disableNoMatch.setText("");
        } else {
            this.disableNoMatch= view.findViewById(R.id.noRequest);
            Log.d("disableNoMatch", disableNoMatch.toString());
            this.disableNoMatch.setText("You don't have any follow request for the moment !\nWe will notify you when you get one.");
        }

        this.backArrow = view.findViewById(R.id.follow_request_back_arrow);

        this.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Status", "Clicked !!");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
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
                // Dialog to accept a follow
                AlertDialog.Builder acceptFollowDialog = new AlertDialog.Builder(getContext());
                acceptFollowDialog.setTitle("Accept follow");
                String message = "Do you allow " + followRequest.get(position).username + " to follow you?\n" +
                        "By accepting this request you will allow the user to able to see all your profile's info's.";
                acceptFollowDialog.setMessage(message);
                acceptFollowDialog.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User acceptedUser = followRequest.get(position);
                        FollowDAO followDAO = new FollowDAO(getContext());
                        followDAO.setPending(mainUser, followRequest.get(position), true);
                        followRequest.remove(position);
                        followRequestListAdapter.notifyItemRemoved(position);
                        followBackProcess(acceptedUser);
                    }
                }).setNeutralButton("I'm not sure yet.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setNegativeButton("No, thanks.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FollowDAO followDAO = new FollowDAO(getContext());
                        followDAO.setPending(mainUser, followRequest.get(position), false);
                        followRequest.remove(position);
                        followRequestListAdapter.notifyItemRemoved(position);
                    }
                });

                acceptFollowDialog.show();
            }
        });

        return view;
    }

    // Dialog to followBack

    private void followBackProcess(final User acceptedUser) {
        Log.d("followBackProcess", "IM GETTING INNNN");
        AlertDialog.Builder followBackDialog = new AlertDialog.Builder(getContext());
        followBackDialog.setTitle("Follow back");
        String followBackMessage;
        if (acceptedUser.privacy == 1) {
            followBackMessage = "You have just allowed " + acceptedUser + " follow you!\n" +
                    "Do you want to follow this user too?";
        } else {
            followBackMessage = "You have just allowed " + acceptedUser + " follow you!\n" +
                    "Do you want to follow this user too? " + acceptedUser + "'s account is private too." +
                    "You will then send a follow request and once you will have sent it you won't be able to go back.";
        }
        followBackDialog.setMessage(followBackMessage);
        followBackDialog.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FollowDAO followDAO = new FollowDAO(getContext());
                followDAO.addFollow(mainUser, acceptedUser);
            }
        }).setNegativeButton("I'm not sure yet.", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

}
