package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
                Intent go_to_home = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(go_to_home);
                finish();
            }
        });
    }
}
