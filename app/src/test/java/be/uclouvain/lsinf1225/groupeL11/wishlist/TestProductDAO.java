package be.uclouvain.lsinf1225.groupeL11.wishlist;

import org.junit.Test;

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

public class TestProductDAO {
    @Test
    public void test_find_by_wishlist(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            WishListDAO wishlist_dao = new WishListDAO(conn);
            UserDAO user_dao = new UserDAO(conn);
            ProductDAO product_dao = new ProductDAO(conn);
            User user = user_dao.find("tomhanks@gmail.com");
            ArrayList<WishList> wishlist_list = wishlist_dao.findByUser(user);
            ArrayList<Product> product_list = product_dao.findByWishList(wishlist_list.get(0));
            assertNotNull(product_list);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
