package be.uclouvain.lsinf1225.groupeL11.wishlist.Interface;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

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

    private Adapter doMySearch(String query) {
        // TODO for return type see : https://developer.android.com/guide/topics/search/search-dialog#PresentingTheResults
        Context context = getApplicationContext();
        if (userDAO == null){
            userDAO = new UserDAO(context);
        }
        this.data = getIntent().getExtras(); // getting bundle from other Activity
        this.mainUser = data.getParcelable(getApplicationContext().getString(R.string.mainUserBundleParcable)); // get string form string.xml

        ArrayList<User> users = userDAO.getAllUser(mainUser.id);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                getUsername(users) );
        return arrayAdapter;
    }

    private ArrayList<String> getUsername(ArrayList<User> users) {
        ArrayList<String> usernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            usernames.add(users.get(i).username);
        }
        return usernames;
    }

}
