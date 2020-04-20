package be.uclouvain.lsinf1225.groupeL11.wishlist;

import android.os.storage.StorageManager;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;
import java.util.ArrayList;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;



public class TestWishListDAO {

    @Test
    public void test_create() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            UserDAO userDAO = new UserDAO(conn);
            User user = userDAO.find("testuser@email.com");
            assertNotNull(user);
            WishListDAO wishListDAO = new WishListDAO(conn);
            WishList new_wishlist = new WishList(-1);
            new_wishlist.name = "Test";
            new_wishlist.description = "Test Wishlist";
            new_wishlist.user = user;
            boolean err = wishListDAO.create(new_wishlist);
            assertTrue(err);
            wishListDAO.delete(new_wishlist);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_with_null_user() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            WishListDAO dao = new WishListDAO(conn);
            WishList list = new WishList(-1);
            assertFalse(dao.create(list));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_with_non_existing_user() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            User usr = new User(-1);
            WishListDAO dao = new WishListDAO(conn);
            WishList list = new WishList(-1);
            list.user = usr;
            assertFalse(dao.create(list));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_delete() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            WishListDAO dao = new WishListDAO(conn);
            UserDAO u_dao = new UserDAO(conn);
            User user = u_dao.find("testuser@email.com");
            WishList list = new WishList(-1);
            list.name = "Test";
            list.description = "Test Wishlist";
            list.user = user;
            dao.create(list);
            boolean err = dao.delete(list);
            assertTrue(err);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_find() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            UserDAO dao = new UserDAO(conn);
            User user = dao.find("testuser@email.com");
            WishListDAO list_dao = new WishListDAO(conn);
            ArrayList<WishList> list = list_dao.findByUser(user);
            assertNotNull(list);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_update() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            WishListDAO list_dao = new WishListDAO(conn);
            UserDAO user_dao = new UserDAO(conn);
            String old_name = "Test";

            User user = user_dao.find("testuser@email.com");
            WishList list = new WishList(-1);
            list.name = old_name;
            list.description = "Test Wishlist";
            list.user = user;
            list_dao.create(list);

            list.name = "Test update";
            list_dao.update(list);

            ArrayList<WishList> find_list = list_dao.findByUser(user);
            assertNotEquals(find_list.get(0).name, old_name);
            list_dao.delete(list);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
