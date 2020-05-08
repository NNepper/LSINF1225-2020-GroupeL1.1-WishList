package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Interest;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.InterestDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.InterestsListAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.SearchUsersResultAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class InterestsFragment extends Fragment {

    private RecyclerView interestsListRecyclerView;
    private InterestsListAdapter interestsListAdapter;
    private RecyclerView.LayoutManager interestsListLayoutManager;
    private User mainUser;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        final InterestDAO interestDAO = new InterestDAO(getContext());
        final ArrayList<Interest> interestsList = interestDAO.getAllInterests();
        final View view = inflater.inflate(R.layout.fragment_interests, container, false);

        interestsListRecyclerView = view.findViewById(R.id.interests_recycler_view);
        interestsListRecyclerView.setHasFixedSize(true);
        interestsListLayoutManager = new LinearLayoutManager(view.getContext());
        interestsListAdapter = new InterestsListAdapter(interestsList, getContext(), mainUser);

        interestsListRecyclerView.setLayoutManager(interestsListLayoutManager);
        interestsListRecyclerView.setAdapter(interestsListAdapter);

        interestsListAdapter.setOnItemClickLister(new InterestsListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onCheckClick(int position) {
                // TODO gérer ce qu'il se passe quand on check la box !!!
                Interest interest = interestsList.get(position);
                UserDAO userDAO = new UserDAO(getContext());
                ArrayList<Interest> userInterests = interestDAO.readInterests(mainUser.id);
                // Inverse le status de l'intérêt
                boolean checked = isActiveInterest(interest, userInterests);
                if (checked) {
                    Log.d("Interest", "This is an ACTIVE interest, it must be unchecked");
                }
                else {
                    Log.d("Interest", "This is an DOWN interest, it must be checked");
                }
                userDAO.setInterest(mainUser, interest, ! isActiveInterest(interest, userInterests));
            }
        });

        return view;
    }

    private boolean isActiveInterest(Interest interest, ArrayList<Interest> interests) {
        for (Interest current : interests) {
            if (current.getInterestName().equals(interest.getInterestName())) {
                return true;
            }
        }
        return false;
    }
}
