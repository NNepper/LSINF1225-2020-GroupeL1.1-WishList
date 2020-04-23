package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupeL11.wishlist.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ProfileCreationActivity extends AppCompatActivity {

    Spinner dropdownColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        dropdownColor = findViewById(R.id.newprofile_color);
        String[] testItems = {"Choose color", "Color: Red", "Color: Blue", "Color: Green"};


        //TODO: adapter layout for dropdown
        dropdownColor.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                testItems
        ));

    }
}
