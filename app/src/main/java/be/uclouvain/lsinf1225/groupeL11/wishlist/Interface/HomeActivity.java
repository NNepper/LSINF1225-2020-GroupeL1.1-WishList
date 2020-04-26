package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class HomeActivity extends AppCompatActivity {

    private Bundle data;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        data = savedInstanceState;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav); //find the bottom navigation bar
        bottomNav.setOnNavigationItemSelectedListener(navlistener); //give the navigation listener to the navigation bar

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FollowsFragment()).commit(); //display the default fragment

        // Creating mainFragment and replacing with the Follow's Fragment
        Fragment newFragment = new FollowsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new FollowsFragment()).commit();
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

                    transaction.replace(R.id.fragment_container, selectedFragment).commit(); //display the clicked fragment
                    return true;
                }
            };
}
