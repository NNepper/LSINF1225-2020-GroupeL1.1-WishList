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

public class LoginActivity extends AppCompatActivity {

    private ImageView backArrow;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getSupportActionBar().hide(); remove top bar

        this.backArrow = (ImageView) findViewById(R.id.login_back_arrow);
        this.submit = findViewById(R.id.login_submit);

        this.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_to_main_page = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_to_main_page);
                finish();
            }
        });

        this.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.login_password)).getText().toString();

                if (email.length() == 0 || password.length() == 0){
                    CharSequence toastText = "Please enter all infos";


                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
                else {
                    User mainUser = null;
                    // TODO login with infos inside DB
                    Intent go_to_home = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(go_to_home);
                    finish();
                    }

            }
        });
    }
}



