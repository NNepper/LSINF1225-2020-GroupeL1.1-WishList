package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.R;

public class SearchActivity extends ListActivity {

    private Bundle data;
    public User mainUser;
    UserDAO userDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    public ArrayAdapter<User> doMySearch(String query) {
        Context context = getApplicationContext();
        if (userDAO == null){
            userDAO = new UserDAO(context);
        }
        this.data = getIntent().getExtras(); // getting bundle from other Activity
        assert data != null;
        this.mainUser = data.getParcelable(getApplicationContext().getString(R.string.mainUserBundleParcable)); // get string form string.xml

        assert mainUser != null;
        ArrayList<User> users = userDAO.getAllUser(mainUser.id);
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                filter(users, query) );
        return arrayAdapter;
    }

    private ArrayList<User> filter(ArrayList<User> users, String query) {
        ArrayList<User> filtered = new ArrayList<>();
        for (User user : users) {
            if (user.username.contains(query)) {
                filtered.add(user);
            }
        }
        return filtered;
    }
}
