package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import java.util.ArrayList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.*;

public class User {
    private String emailAddress;
    private String password;

    /* User's constructor */
    public User(String email, String password,) {
        DAO DAO = new DAO();

        // Check if the given emailAddress is linked to a User account
        if (!DAO.checkExistence(email)) {
            throw new RuntimeException("The given emailAddress doesn't exist");
        }

        // Check if the given password is right
        if (!DAO.checkPassword(email, password)) {
            throw new RuntimeException("Password incorrect");
        }

        this.emailAddress = email;
        this.password = password;
    }

    // Getter username
    public String getUsername() {
        return DAO.getUsername(this.emailAddress);
    }

    // Getter firstname
    public String getFirstname() {
        return DAO.getFirsname(this.emailAddress);
    }

    // Getter lastname
    public String getLastname() {
        return DAO.getLastname(this.emailAddress);
    }

    // Getter color
    public String getColor() {
        return DAO.getColor(this.emailAddress);
    }

    // Getter tshirtSize
    public String getTshirtSize() {
        return DAO.getTshirtSize(this.emailAddress);
    }

    // Getter shoesSize
    public String getShoesSize() {
        return DAO.getShoesSize(this.emailAddress);
    }

    // Getter address
    public String getAdress() {
        return DAO.getAddress(this.emailAddress);
    }

    // Getter privacy
    public Boolean getPrivacy() {
        return DAO.getPrivacy(this.emailAddress);
    }

    // Getter interests
    public ArrayList<Interest> getInterests() {
        return DAO.getInterests(this.emailAddress);
    }

    // Getter following
    public ArrayList<User> getFollowing() {
        return DAO.getFollowing(this.emailAddress);
    }

    // Getter wishLists
    public ArrayList<WishList> getWishList() {
        return DAO.getWishLists(this.emailAddress);
    }
}

