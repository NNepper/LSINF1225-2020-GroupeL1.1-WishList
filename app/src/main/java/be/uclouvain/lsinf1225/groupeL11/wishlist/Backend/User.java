package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User {
    private String emailAddress;
    private String password;
    private String username;
    private String firstname;
    private String lastname;
    private String color;
    private String tshirtSize;
    private String shoesSize;
    private String address;
    private Boolean privacy;
    private ArrayList<Interest> interests;
    private ArrayList<User> following;
    private ArrayList<WishList> wishlists;


    /* User's constructor */
    public User(String email, String password) {
        UserDAO UserDAO = new UserDAO();

        // Check if the given emailAddress is linked to a User account
        if (!UserDAO.checkExistence(email)) {
            throw new RuntimeException("The given emailAddress doesn't exist");
        }

        // Check if the given password is right
        if (!UserDAO.checkPassword(email, password)) {
            throw new RuntimeException("Password incorrect");
        }

        this.emailAddress = email;
        this.password = password;
        this.username = UserDAO.getUsername(this.emailAddress);
        this.firstname = UserDAO.getFirsname(this.emailAddress);
        this.lastname = UserDAO.getLastname(this.emailAddress);
        this.color = UserDAO.getColor(this.emailAddress);
        this.tshirtSize = UserDAO.getTshirtSize(this.emailAddress);
        this.shoesSize = UserDAO.getShoesSize(this.emailAddress);
        this.address = UserDAO.getAddress(this.emailAddress);
        this.privacy = UserDAO.getPrivacy(this.emailAddress);
        this.interests = UserDAO.getInterests(this.emailAddress);
        this.following = UserDAO.getFollowing(this.emailAddress);
        this.wishlists = UserDAO.getWishLists(this.emailAddress);

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
    public String getShoesSize() {
        return this.shoesSize;
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
    public ArrayList<WishList> getWishList() {
        return this.wishlists;
    }
}

