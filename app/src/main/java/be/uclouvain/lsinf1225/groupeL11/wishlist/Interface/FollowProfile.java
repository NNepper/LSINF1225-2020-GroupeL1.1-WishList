package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;


public class FollowProfile extends Fragment {

    private TextView username;
    private TextView address;
    private TextView shoesSize;
    private TextView tShirtSize;
    private TextView trouserSize;
    private TextView favColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow_profile, container, false);

        final Bundle bundle = this.getArguments();
        UserDAO userDAO = new UserDAO(getContext());
        final User following_user = userDAO.get(bundle.getInt("followingUserID"), null);

        username = view.findViewById(R.id.follow_profile_username);
        address = view.findViewById(R.id.follow_profile_address);
        shoesSize = view.findViewById(R.id.follow_profile_shoes_size);
        tShirtSize = view.findViewById(R.id.follow_profile_thsirt_size);
        trouserSize = view.findViewById(R.id.follow_profile_trouser_size);
        favColor = view.findViewById(R.id.follow_profile_fav_color);

        username.setText(following_user.username);
        address.setText(following_user.address);
        shoesSize.setText("Shoe Size: " + following_user.shoeSize);
        tShirtSize.setText("TShirt Size: " + following_user.tshirtSize);
        trouserSize.setText("Trouser Size: " + following_user.trouserSize);
        favColor.setText("Favorite Color: " + following_user.color);


        return view;
    }
}
