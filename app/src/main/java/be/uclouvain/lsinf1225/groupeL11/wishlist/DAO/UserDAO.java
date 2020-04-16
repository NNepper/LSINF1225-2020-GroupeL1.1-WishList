package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.*;
import java.util.ArrayList;

public class UserDAO {

    public UserDAO(){
        //TODO
    }

    public static boolean checkExistence(String emailAddress) {
        //TODO
        return true;
    }

    public static boolean checkPassword(String emailAddress, String password) {
        //TODO
        return true;
    }

    public static String getUsername(String emailAddress) {
        //TODO
        return null;
    }

    public static String getFirsname(String emailAddress) {
        //TODO
        return null;
    }

    public static String getLastname(String emailAddress) {
        //TODO
        return null;
    }

    public static String getColor(String emailAddress) {
        //TODO
        return null;
    }

    public static String getTshirtSize(String emailAddress) {
        //TODO
        return null;
    }

    public static String getShoesSize(String emailAddress) {
        //TODO
        return null;
    }

    public static String getAddress(String emailAddress) {
        //TODO
        return null;
    }

    public static Boolean getPrivacy(String emailAddress) {
        //TODO
        return null;
    }

    public static ArrayList<Interest> getInterests(String emailAddress) {
        //TODO
        return null;
    }

    public static ArrayList<User> getFollowing(String emailAddress) {
        //TODO
        return null;
    }

    public static ArrayList<WishList> getWishLists(String emailAddress) {
        WishListDAO DAO = new WishListDAO();

        /*
         * TODO: Faire l'appel SQL avec emailAddress pour avoir les ID SQL des différentes WishList pour init
         *      les instances WishList avec WishListDAO, qui vont à leur tour appeller les ProductDAO pour init
         *      les product composant les WishLists
         */
        return null;
    }

    public static void addFollow(User follower, User followed, boolean pending) {
        //TODO Ajouter l'utilisateur followed à la liste following de follower avec le pending
    }

    public static void setFollow(User follower, User followed, boolean pending) {
        //TODO Ajouter l'utilisateur followed à la liste following de follower avec le pending
    }
}
