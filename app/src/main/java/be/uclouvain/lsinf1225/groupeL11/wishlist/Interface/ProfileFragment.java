package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.LinkedList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Interface.Adapter.SearchUsersResultAdapter;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class ProfileFragment extends Fragment {

    private TextView usernameTextView, emailTextView, addressTextView;
    private ImageView logOut;
    private ImageView confirm;
    private Switch privacySwitch;
    private User mainUser;
    private Spinner shoeSizeSpinner, trouserSizeSpinner, tShirtSizeSpinner, colorSpinner;
    private Button interestButton, followRequestButton;
    private UserDAO userDAO;
    private String tShirtSizeChanged, trouserSizeChanged, colorChanged;
    private int shoeSizeChanged;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userDAO = new UserDAO(getContext());
        this.mainUser = ((HomeActivity) getActivity()).mainUser;
        final View view = inflater.inflate(R.layout.fragment_home_profile, container, false);

        usernameTextView = view.findViewById(R.id.profileUsername);
        usernameTextView.setText(mainUser.username);

        emailTextView = view.findViewById(R.id.profileEmail);
        emailTextView.setText(mainUser.email);

        addressTextView = view.findViewById(R.id.profileAddress);
        addressTextView.setText(mainUser.address);

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
                updateMainUser();
                userDAO.update(mainUser);
                CharSequence toastText = "Infos updated";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getContext(), toastText,  duration);
                toast.show();
            }
        });

        privacySwitch = view.findViewById(R.id.profilePrivacySwitch);
        privacySwitch.setChecked(mainUser.privacy == 1);
        privacySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainUser.privacy = mainUser.privacy == 0 ? 1 : 0;
                privacySwitch.forceLayout();
                userDAO.updatePrivacy(mainUser); // don't update
            }
        });

        // Setting spinner for the shoes sizes

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
                if(position> 0) shoeSizeChanged = 34+position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing here since user didn't change his shoe size
            }
        });

        // Setting spinner for the Tshirt sizes

        tShirtSizeSpinner = view.findViewById(R.id.profileSpinnerTshirtSize);

        final String[] tShirtSize = {"", "XS", "S", "M", "L", "XL", "XXL"};
        tShirtSize[0] = "Tshirt size: " + mainUser.tshirtSize;

        tShirtSizeSpinner.setAdapter(new ArrayAdapter<String>(
                getContext(),
                R.layout.adapter_dropdown_profile,
                R.id.dropdown_title,
                tShirtSize
        ));

        tShirtSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) tShirtSizeChanged = tShirtSize[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing here since user didn't change his shoe size
            }
        });

        // Setting spinner for the trouser sizes

        trouserSizeSpinner = view.findViewById(R.id.profileSpinnerTrouserSize);

        final String[] trouserSizes = {"", "XS", "S", "M", "L", "XL", "XXL"};
        trouserSizes[0] = "Trouser size: " + mainUser.tshirtSize;

        trouserSizeSpinner.setAdapter(new ArrayAdapter<String>(
                getContext(),
                R.layout.adapter_dropdown_profile,
                R.id.dropdown_title,
                trouserSizes
        ));

        trouserSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) trouserSizeChanged = trouserSizes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing here since user didn't change his shoe size
            }
        });

        // Setting Spinner for color

        colorSpinner = view.findViewById(R.id.profileSpinnerColor);

        final String[] colors = {"", "Red", "Green", "Blue"};
        colors[0] = "Color: " + mainUser.color;

        colorSpinner.setAdapter(new ArrayAdapter<String>(
                getContext(),
                R.layout.adapter_dropdown_profile,
                R.id.dropdown_title,
                colors
        ));

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0) colorChanged = colors[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing here since user didn't change his shoe size
            }
        });

        this.followRequestButton = view.findViewById(R.id.see_requests);
        Log.d("noRequestButton", followRequestButton.toString());
        this.followRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new FollowRequestFragment()).commit();
            }
        });

        return view;
    }

    private void updateMainUser(){
        if(tShirtSizeChanged != null && mainUser.tshirtSize.compareTo(tShirtSizeChanged) != 0) mainUser.tshirtSize = tShirtSizeChanged;
        if(trouserSizeChanged != null && mainUser.trouserSize.compareTo(trouserSizeChanged) != 0) mainUser.trouserSize = trouserSizeChanged;
        if(shoeSizeChanged != 0 && mainUser.shoeSize != shoeSizeChanged) mainUser.shoeSize = shoeSizeChanged;
        if(mainUser.color != colorChanged) mainUser.color = colorChanged;
        if(mainUser.username.compareTo(usernameTextView.getText().toString()) != 0) mainUser.username = usernameTextView.getText().toString();
        if(mainUser.email.compareTo(emailTextView.getText().toString()) != 0) mainUser.email = emailTextView.getText().toString();
        if(mainUser.address.compareTo(addressTextView.getText().toString()) != 0) mainUser.address = addressTextView.getText().toString();
    }
}
