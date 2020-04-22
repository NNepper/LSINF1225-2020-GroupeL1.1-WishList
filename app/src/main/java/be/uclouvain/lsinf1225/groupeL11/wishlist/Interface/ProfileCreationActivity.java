package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupeL11.wishlist.R;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ProfileCreationActivity extends AppCompatActivity {

    Spinner drowndownColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        drowndownColor = findViewById(R.id.newprofile_color);
        String[] testItems = {"Red", "Blue", "Green"};


        //TODO: adapter layout for dropdown
        drowndownColor.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_wishlist_item,
                R.id.item_wishlist_title,
                testItems
        ));

    }
}
