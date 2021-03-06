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

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class RegisterActivity extends AppCompatActivity {

    private Button submitButton;
    private Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.submitButton = (Button) findViewById(R.id.submitButton);

        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();

                String username = ((EditText) findViewById(R.id.Username)).getText().toString();
                String password = ((EditText) findViewById(R.id.Password)).getText().toString();
                String confPassword = ((EditText) findViewById(R.id.ConfirmPassword)).getText().toString();
                String email = ((EditText) findViewById(R.id.Email)).getText().toString();

                UserDAO userDAO = new UserDAO(getApplicationContext());


                if (username.length() == 0 || password.length() == 0 || confPassword.length() == 0 || email.length() == 0){
                    CharSequence toastText = "Please enter all infos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
                else if(!userDAO.check(email)){
                    CharSequence text = "This email address is already used";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else if (password.compareTo(confPassword) == 0) {
                    User mainUser = new User(email, username, password);

                    // Bundle for easy Object storage
                    Bundle data = new Bundle();
                    data.putString("username", username);
                    data.putString("password", password);
                    data.putString("email", email);

                    // Start new Activity and pass data to the next Activity
                    Intent ProfilCreation = new Intent(getApplicationContext(), ProfileCreationActivity.class); // change the intent to let the user enter his infos
                    ProfilCreation.putExtras(data);
                    startActivity(ProfilCreation);
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