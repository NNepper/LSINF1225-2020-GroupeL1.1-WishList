package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

public class FollowDAO extends MyDatabaseHelper {
    public FollowDAO(Context context) {
        super(context);
    }

    public ArrayList<User> getFollowing(int userID){
        return null;
    }
}
