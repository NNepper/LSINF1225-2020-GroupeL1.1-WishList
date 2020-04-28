package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Interest;

public class InterestDAO extends MyDatabaseHelper{
    public InterestDAO(Context context) {
        super(context);
    }

    public ArrayList<Interest> getInterests(int userID){
        //TODO
        return null;
    }
}
