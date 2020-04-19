package be.uclouvain.lsinf1225.groupeL11.wishlist;

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
            User user = userDAO.find("tomhanks@gmail.com");
            assertNotNull(user);
            WishListDAO wishListDAO = new WishListDAO(conn);
            WishList new_wishlist = new WishList("Test", "WishList test", user);
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
            User usr = new User("Test", "User", "email", "pass", "clingier", "blue", "small", 42, "emile dury");
            WishListDAO dao = new WishListDAO(conn);
            WishList list = new WishList("test", "test list", null);
            assertFalse(dao.create(list));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_with_non_existing_user() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try (Connection conn = DriverManager.getConnection(url)) {
            User usr = new User("Test", "User", "email", "pass", "clingier", "blue", "small", 42, "emile dury");
            WishListDAO dao = new WishListDAO(conn);
            WishList list = new WishList("test", "test list", usr);
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
            User user = u_dao.find("tomhanks@gmail.com");
            WishList list = new WishList("test", "Test Wishlist", user);
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
            User user = dao.find("tomhanks@gmail.com");
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

            User user = user_dao.find("tomhanks@gmail.com");
            ArrayList<WishList> lists = list_dao.findByUser(user);
            String old_name = lists.get(0).getName();
            lists.get(0).setName("Test update");
            list_dao.update(lists.get(0));
            ArrayList<WishList> new_find = list_dao.findByUser(user);
            assertNotEquals(new_find.get(0).getName(), old_name);
            lists.get(0).setName("Test 1");
            list_dao.update(lists.get(0));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
