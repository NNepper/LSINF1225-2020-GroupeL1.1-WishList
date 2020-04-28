package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.content.Context;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

public class WishListDAO extends MyDatabaseHelper{
    public WishListDAO(Context context) {
        super(context);
    }

    public void updateUser(){

    }

    public Boolean addWishList(){
        return true;
    }

    public ArrayList<WishList> getWishLists(int userID){
        return null;
    }

    public WishList getWishList(int userID){
        return null;
    }
}
