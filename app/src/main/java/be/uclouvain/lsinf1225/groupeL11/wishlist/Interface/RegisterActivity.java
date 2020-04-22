package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView backArrow;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.backArrow = (ImageView) findViewById(R.id.BackHomeArrow);
        this.submitButton = (Button) findViewById(R.id.submitButton);

        this.backArrow.setOnClickListener(new View.OnClickListener() { // back arrow to get back to home page
            @Override
            public void onClick(View v) {
                    // Start new Activity and pass data to the next Activity
                    Intent back_to_home_page = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(back_to_home_page);
                    finish();
            }
        });

        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();

                String username = ((EditText) findViewById(R.id.Username)).getText().toString();
                String password = ((EditText) findViewById(R.id.Password)).getText().toString();
                String confPassword = ((EditText) findViewById(R.id.ConfirmPassword)).getText().toString();
                String email = ((EditText) findViewById(R.id.Email)).getText().toString();


                if (username.length() == 0 || password.length() == 0 || confPassword.length() == 0 || email.length() == 0){
                    CharSequence toastText = "Please enter all infos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
                // TODO check if user doesn't exist in DB
                else if (password.compareTo(confPassword) == 0) {
                    User mainUser = new User(-1); // maybe change/add the constructor of User
                    // User mainUser = new User(-1, email, username, password)
                    mainUser.firstname = null;
                    mainUser.lastname = null;
                    mainUser.email = email;
                    mainUser.password = password;
                    mainUser.username = username;
                    mainUser.color = null;
                    mainUser.tshirtSize = null;
                    mainUser.trouserSize = null;
                    mainUser.shoeSize = 0;
                    mainUser.address = null;

                    // Bundle for easy Object storage
                    Bundle data = new Bundle();
                    //data.putSerializable("mainUser", (Serializable) mainUser);

                    // Start new Activity and pass data to the next Activity
                    Intent back_to_home_page = new Intent(getApplicationContext(), ProfileCreationActivity.class); // change the intent to let the user enter his infos
                    back_to_home_page.putExtras(data);
                    startActivity(back_to_home_page);
                    finish();
                }
                else {
                    CharSequence text = "Recheck the password you entered..";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }
}