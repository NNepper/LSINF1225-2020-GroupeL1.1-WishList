package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView;
    private ImageView logOut;
    private ImageView confirm;
    private Switch privacySwitch;
    private User mainUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        final View view = inflater.inflate(R.layout.fragment_home_profile, container, false);

        usernameTextView = view.findViewById(R.id.profileUsername);
        usernameTextView.setText(mainUser.username);

        logOut = view.findViewById(R.id.profileLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Maybe use a dialog to avoid miss click ?
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

        return view;
    }
}
