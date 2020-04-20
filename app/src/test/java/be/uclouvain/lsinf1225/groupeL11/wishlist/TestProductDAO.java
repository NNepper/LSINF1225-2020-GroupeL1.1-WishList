package be.uclouvain.lsinf1225.groupeL11.wishlist;

import org.junit.Test;
import junit.framework.TestCase;

import static org.junit.Assert.*;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.ProductDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import  be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.WishListDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;


public class TestProductDAO extends TestCase{

//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        String url = "jdbc:sqlite:db/bdd.sqlite";
//        Connection connection = DriverManager.getConnection(url);
//        this.conn = connection;
//        this.user_dao = new UserDAO(conn);
//        this.product_dao = new ProductDAO(conn);
//        this.wishlist_dao = new WishListDAO(conn);
//    }

    @Test
    public void test_find_by_wishlist(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)) {
            UserDAO user_dao = new UserDAO(conn);
            ProductDAO product_dao = new ProductDAO(conn);
            WishListDAO wishlist_dao = new WishListDAO(conn);
            User user = user_dao.find("tomhanks@gmail.com");
            ArrayList<WishList> wishlist_list = wishlist_dao.findByUser(user);
            ArrayList<Product> product_list = product_dao.findByWishList(wishlist_list.get(0));
            assertNotNull(product_list);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_update_when_non_existing_relation_between_list_and_product(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)) {
            UserDAO user_dao = new UserDAO(conn);
            ProductDAO product_dao = new ProductDAO(conn);
            WishListDAO wishlist_dao = new WishListDAO(conn);
            User user = user_dao.find("tomhanks@gmail.com");
            WishList list = wishlist_dao.findByUser(user).get(0);
            Product product_to_be_added = product_dao.find("Ballon");
            list.addProduct(product_to_be_added);
            wishlist_dao.update(list);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
