package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.backArrow = (ImageView) findViewById(R.id.BackHomeArrow);

        this.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = (EditText) findViewById(R.id.Username);
                EditText editText2 = (EditText) findViewById(R.id.Password);
                EditText editText3 = (EditText) findViewById(R.id.ConfirmPassword);
                EditText editText4 = (EditText) findViewById(R.id.Email);

                String username = editText1.getText().toString();
                String password = editText2.getText().toString();
                String confPassword = editText3.getText().toString();
                String email = editText4.getText().toString();

                if (password == confPassword) {
                    User mainUser = new User(-1);
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
                    data.putSerializable("mainUser", (Serializable) mainUser);

                    // Start new Activity and pass data to the next Activity
                    Intent back_to_home_page = new Intent(getApplicationContext(), MainActivity.class);
                    back_to_home_page.putExtras(data);
                    startActivity(back_to_home_page);
                    finish();
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "Recheck the password you entered..";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }
}