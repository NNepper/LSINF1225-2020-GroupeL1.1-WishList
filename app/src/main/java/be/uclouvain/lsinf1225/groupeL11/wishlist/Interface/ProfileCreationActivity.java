package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class ProfileCreationActivity extends AppCompatActivity {

    Spinner dropdownColor, dropdownShoes, dropdownTshirt, dropdownTrousers;
    Button submitNewProfil;

    String color, tshirt, trouser;
    int shoes;

    User mainUser = getIntent().getExtras().getParcelable("mainUser");

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
                        shoes = 30;
                        break;
                    case 2:
                        shoes = 31;
                        break;
                    case 3:
                        shoes = 32;
                        break;
                    case 4:
                        shoes = 33;
                        break;
                    case 5:
                        shoes = 34;
                        break;
                    case 6:
                        shoes = 35;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { shoes = 0; }
        });


        /** Trouser Size **/

        dropdownTrousers = findViewById(R.id.newprofile_trousers);

        // To be completed (Laisser le choix d'ajouter à l'utilisateur ?)(xx/xx la forme ??)
        final String[] trouserItems = {"28", "30", "32", "34", "36"};

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
                        trouser = "28";
                        break;
                    case 2:
                        trouser = "30";
                        break;
                    case 3:
                        trouser = "32";
                        break;
                    case 4:
                        trouser = "34";
                        break;
                    case 5:
                        trouser = "36";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { trouser = null; }
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

        /** Submit informations **/
        this.submitNewProfil = (Button) findViewById(R.id.newprofile_submit);
        this.submitNewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();

                String firstname = ((EditText) findViewById(R.id.newprofile_firstname)).getText().toString();
                String lastname = ((EditText) findViewById(R.id.newprofile_lastname)).getText().toString();
                String city = ((EditText) findViewById(R.id.new_profile_city)).getText().toString();
                String street = ((EditText) findViewById(R.id.new_profile_street)).getText().toString();
                String zip = ((EditText) findViewById(R.id.new_profile_zip)).getText().toString();

                if (firstname.length() == 0 || lastname.length() == 0 || city.length() == 0
                        || street.length() == 0 || zip.length() == 0){

                    CharSequence toastText = "Please enter all infos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
                else {
                    //Update mainUser Data
                    mainUser.firstname = firstname;
                    mainUser.lastname = lastname;
                    //TODO: Address problem, to be cleared
                    //mainUser.city = city;
                    //mainUSer.street = street;
                    //mainUser.zip = zip;
                    mainUser.shoeSize = shoes;
                    mainUser.trouserSize = trouser;
                    mainUser.tshirtSize = tshirt;

                    //TODO: Update DAO ??
                }

                // Bundle for easy Object storage
                Bundle data = new Bundle();
                data.putParcelable("mainUser", mainUser);

                // Start new Activity and pass data to the next Activity
                Intent HomeActivity = new Intent(getApplicationContext(), ProfileCreationActivity.class);
                HomeActivity.putExtras(data);
                startActivity(HomeActivity);
            }
        });



    }

}
