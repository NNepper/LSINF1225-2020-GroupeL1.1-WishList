package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

        this.backArrow = (ImageView) findViewById(R.id.BackHomeArrow);
        this.submit = findViewById(R.id.button);

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

            }
        });
    }

    public void LoginActivity() {
        EditText editText1 = (EditText) findViewById(R.id.Email);
        String email = editText1.getText().toString();

        EditText editText2 = (EditText) findViewById(R.id.Password);
        String password = editText2.getText().toString();

        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User user = dao.find(email);
            Intent go_to_home = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(go_to_home);
            finish();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}



