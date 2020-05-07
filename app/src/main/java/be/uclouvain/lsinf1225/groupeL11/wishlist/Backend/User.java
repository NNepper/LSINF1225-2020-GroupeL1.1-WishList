package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    private String password;
    public String username;
    public String color;
    public String tshirtSize;
    public String trouserSize;
    public int shoeSize;
    public String address;
    public Integer privacy;
    public ArrayList<Interest> interests;
    public ArrayList<User> following;
    public ArrayList<WishList> wishlists;


    /* User's constructor */
    public User(int id) {
        this.id = id;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = null;
        this.lastname = null;
        this.color = null;
        this.tshirtSize = null;
        this.trouserSize = null;
        this.shoeSize = 0;
        this.address = null;
        this.privacy = 0;
        this.interests = null;
        this.following = null;
        this.wishlists = null;
    }

    public User(int id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstname = null;
        this.lastname = null;
        this.color = null;
        this.tshirtSize = null;
        this.trouserSize = null;
        this.shoeSize = 0;
        this.address = null;
        this.privacy = 0;
        this.interests = null;
        this.following = null;
        this.wishlists = null;
    }

    public User(String email){
        this.email = email;
        // null init to avoid Segfault
        this.firstname = null;
        this.lastname = null;
        this.email = null;
        this.password = null;
        this.username = null;
        this.color = null;
        this.tshirtSize = null;
        this.trouserSize = null;
        this.shoeSize = 0;
        this.address = null;
        this.privacy = 0;
        this.interests = null;
        this.following = null;
        this.wishlists = null;
    }





    public int getId() { return this.id; }

    public void setId(int id){ this.id = id; }



    public String getPassword() { return this.password; }

    public void setPassword(String password) {
        this.password = password;
    }

}

