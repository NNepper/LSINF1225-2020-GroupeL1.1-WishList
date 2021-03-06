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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class LoginActivity extends AppCompatActivity {

    private Button submit;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.submit = findViewById(R.id.login_submit);

        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                if (userDAO == null){
                    userDAO = new UserDAO(context);
                }



                String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

                if (email.length() == 0 || password.length() == 0){
                    CharSequence toastText = "Please enter all infos";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, toastText,  duration);
                    toast.show();
                }
                else {
                    // crash when user click quickly on login button -> db is locked
                    User mainUser = userDAO.read(email);
                    userDAO.close();
                    if (mainUser != null){
                        if (mainUser.getPassword().compareTo(password) == 0){
                            // Bundle for easy Object storage
                            Bundle data = new Bundle();
                            data.putString("mainUser", email);
                            // Start new Activity and pass data to the next Activity
                            Intent HomeActivity = new Intent(getApplicationContext(), HomeActivity.class); // change the intent to let the user enter his infos
                            HomeActivity.putExtras(data);
                            startActivity(HomeActivity);
                            finish();
                        }
                        else {
                            CharSequence toastText = "Wrong password!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, toastText, duration);
                            toast.show();
                        }
                    }
                    else{
                        CharSequence toastText = "Please check your entered informations";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, toastText, duration);
                        toast.show();
                    }
                }
            }
        });
    }
}



