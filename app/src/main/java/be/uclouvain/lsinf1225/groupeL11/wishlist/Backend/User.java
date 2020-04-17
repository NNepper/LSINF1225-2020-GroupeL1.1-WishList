package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private String color;
    private String tshirtSize;
    private int shoeSize;
    private String address;
    private Boolean privacy;
    private ArrayList<Interest> interests;
    private ArrayList<User> following;
    private ArrayList<WishList> wishlists;


    /* User's constructor */
    public User(
            String firstname,
            String lastname,
            String email,
            String password,
            String username,
            String color,
            String tshirtSize,
            int shoeSize,
            String address)
    {
        this.id = -1;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.color = color;
        this.tshirtSize = tshirtSize;
        this.shoeSize = shoeSize;
        this.address = address;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return this.id; }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    // Getter username
    public String getUsername() {
        return this.username;
    }

    // Getter firstname
    public String getFirstname() {
        return this.firstname;
    }

    // Getter lastname
    public String getLastname() {
        return this.lastname;
    }

    // Getter color
    public String getColor() {
        return this.color;
    }

    // Getter tshirtSize
    public String getTshirtSize() {
        return this.tshirtSize;
    }

    // Getter shoesSize
    public int getShoesSize() {
        return this.shoeSize;
    }

    // Getter address
    public String getAddress() {
        return this.address;
    }

    // Getter privacy
    public Boolean getPrivacy() {
        return this.privacy;
    }

    // Getter interests
    public ArrayList<Interest> getInterests() {
        return this.interests;
    }

    // Getter following
    public ArrayList<User> getFollowing() {
        return this.following;
    }

    // Getter wishLists
    public ArrayList<WishList> getWishLists() {
        return this.wishlists;
    }
    /**
    public void addFollow(User toFollow, boolean pending) { UserDAO.addFollow(toFollow, this, pending); }

    public Map<String, Object> getUserInfos() {
        Map<String, Object> data = new HashMap<>();
        data.put("first name", getFirstname());
        data.put("last name", getLastname());
        data.put("address", getAddress());
        data.put("color", getColor());
        data.put("shoes size", getShoesSize());
        data.put("t-shirt size", getTshirtSize());
        data.put("username", getUsername());
        data.put("email address", this.emailAddress);
        if (getPrivacy()) {
            data.put("privacy", "private");
        } else {
            data.put("privacy", "public");
        }
        data.put("following", getFollowing());
        data.put("interests", getInterests());
        data.put("wishlists", getWishLists());
        return data;
    }

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

