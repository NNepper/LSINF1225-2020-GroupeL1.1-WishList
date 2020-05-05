package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class HomeActivity extends AppCompatActivity {

    private Bundle data;
    public User mainUser;
    public ArrayList<User> searchUsersResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UserDAO userDAO = new UserDAO(getApplicationContext());

        this.data = getIntent().getExtras(); // getting bundle from other Activity
        this.mainUser = userDAO.read( data.getString("mainUser") ); // get string form string.xml

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav); //find the bottom navigation bar
        bottomNav.setOnNavigationItemSelectedListener(navlistener); //give the navigation listener to the navigation bar

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FollowsFragment()).commit(); //display the default fragment

        // Creating mainFragment and replacing with the Follow's Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new FollowsFragment()).commit();

        /*for (User user : mainUser.following) { TODO uncomment when getPending is implemented
            if (UserDAO.getPending(mainUser, user)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Follow request");
                builder.setMessage("Hi ! What's up ?\nSome people want to follow you !\nYou can decide to accept or decline from your follow view.");
                builder.setNeutralButton("Great !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { return; }
                });
                builder.show();
                break;
            }
        }*/
    }


    //create the navigation lister
    private BottomNavigationView.OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.nav_follows: //if clicked on follow, display
                            selectedFragment = new FollowsFragment();
                            break;
                        case R.id.nav_wishlist: //if clicked on wishlists
                            selectedFragment = new WishlistFragment();
                            break;
                        case R.id.nav_profile: //if clicked on profile
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    // getIntent().getExtras();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, selectedFragment).commit(); //display the clicked fragment
                    return true;
                }
            };
}
