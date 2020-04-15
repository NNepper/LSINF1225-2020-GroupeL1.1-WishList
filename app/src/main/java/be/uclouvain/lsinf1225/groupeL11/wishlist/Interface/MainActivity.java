package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.login = findViewById(R.id.login); //find the login button
        this.register = findViewById(R.id.register); //find the register button

        //when login is clicked, switch to LoginActivity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(switchToLogin);
                finishActivity(0);
            }
        });


        //when register is clicked, switch to RegisterActivity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(switchToRegister);
                finishActivity(0);
            }
        });
    }
}
