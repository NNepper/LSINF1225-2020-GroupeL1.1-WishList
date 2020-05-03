package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView;
    private ImageView logOut;
    private ImageView confirm;
    private Switch privacySwitch;
    private User mainUser;
    private Spinner shoeSizeSpinner, trouserSizeSpinner, tShirtSizeSpinner;
    private Button interestButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        final View view = inflater.inflate(R.layout.fragment_home_profile, container, false);

        usernameTextView = view.findViewById(R.id.profileUsername);
        usernameTextView.setText(mainUser.username);
        // TODO do the same thing as the line above to set Email address and phone number TextViews with user info

        logOut = view.findViewById(R.id.profileLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Maybe use a dialog to avoid miss click ?
                ((HomeActivity) getActivity()).mainUser = null;
                mainUser = null;
                Intent back_to_main_page = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(back_to_main_page);
                getActivity().finish();
            }
        });

        confirm = view.findViewById(R.id.profileConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO get all infos inside textViews and update them inside DB with DAO
                // TODO when update is done reaload the view with new infos
            }
        });

        privacySwitch = view.findViewById(R.id.profilePrivacySwitch);
        privacySwitch.setChecked(mainUser.privacy == 1);
        privacySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO change drawable in front of switch
                // TODO save changes in DB with DAO
                switch (mainUser.privacy){
                    case 0: mainUser.privacy = 1;
                    case 1: mainUser.privacy = 0;
                    default: privacySwitch.forceLayout();
                }
            }
        });

        shoeSizeSpinner = view.findViewById(R.id.profileSpinnerShoeSize);

        String[] shoeSizes = new String[15];
        shoeSizes[0] = "Shoe size: " + mainUser.shoeSize;
        for (int i=35;i<49;i++) {
            shoeSizes[i - 34] = Integer.toString(i);
        }

        shoeSizeSpinner.setAdapter(new ArrayAdapter<String>(
                getContext(),
                R.layout.adapter_dropdown_profile,
                R.id.dropdown_title,
                shoeSizes
        ));

        shoeSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainUser.shoeSize = 34 + position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing here since user didn't change his shoe size
            }
        });

        // TODO add spinner for TShirt size
        // TODO add spinner for trouser size
        // TODO add spinner for color

        return view;
    }
}
