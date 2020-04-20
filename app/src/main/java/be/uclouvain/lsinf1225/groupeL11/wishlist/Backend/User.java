package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User {
    private int id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public String username;
    public String color;
    public String tshirtSize;
    public String trouserSize;
    public int shoeSize;
    public String address;
    public Boolean privacy;
    public ArrayList<Interest> interests;
    public ArrayList<User> following;
    public ArrayList<WishList> wishlists;


    /* User's constructor */
    public User(int id)
    {
        this.id = id;
    }

    public int getId() { return this.id; }

    public void setId(int id){ this.id = id; }
    /**
    public void askFollow(User followed) {
        if (this.getPrivacy()) {
            UserDAO.addFollow(this, followed, false);
        } else {
            UserDAO.addFollow(this, followed, true);
        }
    }
    public void acceptFollow(User toAccept) {
        UserDAO.setFollow(toAccept, this, true);
    }
     **/
}

