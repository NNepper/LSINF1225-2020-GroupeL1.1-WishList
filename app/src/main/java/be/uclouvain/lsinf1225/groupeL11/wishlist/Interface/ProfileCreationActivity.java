package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
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

    private Bundle data;

    Spinner dropdownColor, dropdownShoes, dropdownTshirt, dropdownTrousers;
    Button submitNewProfil;

    String color, tshirt, trouser;
    int shoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creation);

        data = this.getIntent().getExtras();

        /** Color **/

        dropdownColor = findViewById(R.id.new_profile_color);

        String[] testItems = {"Color: Red", "Color: Blue", "Color: Green"};

        dropdownColor.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                testItems
        ));

        dropdownColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                switch (position) {
                    case 0:
                        color = "Red";
                        break;
                    case 1:
                        color = "Blue";
                        break;
                    case 2:
                        color = "Green";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {  color = "Red";  }
        });

        /** Shoes size **/

        dropdownShoes = findViewById(R.id.newprofile_shoe);

        // To be completed
        String[] shoesItems = {"Shoe size: 35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        dropdownShoes.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                shoesItems
        ));

        dropdownShoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shoes = 35+position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { shoes = 35; }
        });


        /** Trouser Size **/

        dropdownTrousers = findViewById(R.id.newprofile_trousers);

        // To be completed (Laisser le choix d'ajouter à l'utilisateur ?)(xx/xx la forme ??)
        final String[] trouserItems = {"Trouser Size: XS", "S", "M", "L", "XL", "XXL"};

        dropdownTrousers.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                trouserItems
        ));

        dropdownTrousers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) trouser = "XS";
                else trouser = trouserItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { trouser = "XS"; }
        });


        /** Tshirt Size **/

        dropdownTshirt = findViewById(R.id.newprofile_tshirt);

        // To be completed (Laisser le choix d'ajouter à l'utilisateur ?)(xx/xx la forme ??)
        final String[] tshirtItems = {"TShirt Size: XS", "S", "M", "L", "XL", "XXL"};

        dropdownTshirt.setAdapter(new ArrayAdapter<String>(
                this,
                R.layout.adapter_dropdown,
                R.id.dropdown_title,
                tshirtItems
        ));

        dropdownTshirt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) tshirt = "XS";
                else tshirt = tshirtItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { tshirt = "XS"; }
        });

        /** Submit informations **/
        this.submitNewProfil = (Button) findViewById(R.id.newprofile_submit);
        this.submitNewProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                UserDAO userDAO = new UserDAO(context);
                User mainUser = new User(data.getString("email"), data.getString("username"), data.getString("password"));

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
                    return;
                }
                else {
                    //Update mainUser Data
                    mainUser.firstname = firstname;
                    mainUser.lastname = lastname;
                    mainUser.address = street + " " + zip + " " + city;
                    mainUser.shoeSize = shoes;
                    mainUser.trouserSize = trouser;
                    mainUser.tshirtSize = tshirt;
                    mainUser.color = color;
                    userDAO.create(mainUser);
                    userDAO.close();
                }



                // Bundle for easy Object storage
                Bundle data = new Bundle();
                data.putString("mainUser", mainUser.email);

                // Start new Activity and pass data to the next Activity
                Intent HomeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                HomeActivity.putExtras(data);
                startActivity(HomeActivity);
                finish();
            }
        });



    }

}
