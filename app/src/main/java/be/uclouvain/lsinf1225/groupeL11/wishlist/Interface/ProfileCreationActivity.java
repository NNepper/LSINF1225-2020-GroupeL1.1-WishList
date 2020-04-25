package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileCreationActivity extends AppCompatActivity {

    Spinner dropdownColor, dropdownShoes, dropdownTshirt, dropdownTrousers;

    String color, tshirt, trousers, shoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);


        /** Color **/

        dropdownColor = findViewById(R.id.newprofile_color);

        // TODO: Extract every color from DB
        String[] testItems = {"Choose color","Color: Red", "Color: Blue", "Color: Green"};

        dropdownColor.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                testItems
        ));

        //TODO: To be adapted for every data from DB (@DAO)
        dropdownColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                switch (position) {
                    case 1:
                        color = "Red";
                        break;
                    case 2:
                        color = "Blue";
                        break;
                    case 3:
                        color = "Green";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  color = null;  }
        });

        /** Shoes size **/

        dropdownShoes = findViewById(R.id.newprofile_shoe);

        // To be completed
        String[] shoesItems = {"30", "31", "32", "33", "34", "35"};

        dropdownShoes.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                shoesItems
        ));

        dropdownShoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        shoes = "30";
                        break;
                    case 2:
                        shoes = "31";
                        break;
                    case 3:
                        shoes = "32";
                        break;
                    case 4:
                        shoes = "33";
                        break;
                    case 5:
                        shoes = "34";
                        break;
                    case 6:
                        shoes = "35";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { shoes = null; }
        });


        /** Trouser Size **/

        dropdownTrousers = findViewById(R.id.newprofile_trousers);

        // To be completed (Laisser le choix d'ajouter à l'utilisateur ?)(xx/xx la forme ??)
        String[] trouserItems = {"28", "30", "32", "34", "36"};

        dropdownTrousers.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                trouserItems
        ));

        dropdownTrousers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        trousers = "28";
                        break;
                    case 2:
                        trousers = "30";
                        break;
                    case 3:
                        trousers = "32";
                        break;
                    case 4:
                        trousers = "34";
                        break;
                    case 5:
                        trousers = "36";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { trousers = null; }
        });


        /** Tshirt Size **/

        dropdownTshirt = findViewById(R.id.newprofile_tshirt);

        // To be completed (Laisser le choix d'ajouter à l'utilisateur ?)(xx/xx la forme ??)
        String[] tshirtItems = {"XS", "S", "M", "L", "XL", "XXL"};

        dropdownTshirt.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                tshirtItems
        ));

        dropdownTshirt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        tshirt = "XS";
                        break;
                    case 2:
                        tshirt = "S";
                        break;
                    case 3:
                        tshirt = "M";
                        break;
                    case 4:
                        tshirt = "L";
                        break;
                    case 5:
                        tshirt = "XL";
                        break;
                    case 6:
                        tshirt = "XXL";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { tshirt = null; }
        });


    }

}
