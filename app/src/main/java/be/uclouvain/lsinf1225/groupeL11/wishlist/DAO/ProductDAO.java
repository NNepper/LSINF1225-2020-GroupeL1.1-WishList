package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.view.accessibility.AccessibilityRecord;

import org.jetbrains.annotations.NotNull;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.DAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class ProductDAO extends DAO<Product>{

    public ProductDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Product product) {
        String query = "INSERT INTO Products (wishlistID, productID, name, description, link, quantity, position, purchased) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setInt(1, product.wishlist.getId());
            pstmt.setInt(2, product.getId());
            pstmt.setString(3, product.name);
            pstmt.setString(4, product.description);
            pstmt.setString(5, product.link);
            pstmt.setInt(6, product.quantity);
            pstmt.setBoolean(7, product.purchased);
            pstmt.execute();

            ResultSet result = this.connect.createStatement().executeQuery("select last_insert_rowid()");
            product.setId(result.getInt("last_insert_rowid()"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        if(product.getId() != -1) {
            String query = " DELETE FROM Products WHERE productID == ?";
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setInt(1, product.getId());
                pstmt.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean update(Product product){
        String query = "UPDATE Products " +
                "SET (wishlistID, productID, name, description, link, quantity, purchased, position) = " +
                "(?, ?, ?, ?, ?, ?, ?, ?) WHERE productID == ?";
        if(product.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setInt(1, product.wishlist.getId());
                pstmt.setInt(2, product.getId());
                pstmt.setString(3, product.name);
                pstmt.setString(3, product.link);
                pstmt.setInt(4, product.quantity);
                pstmt.setBoolean(5, product.purchased);
                pstmt.setInt(6, product.position);
                pstmt.setInt(4, product.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Product find(String email) {
        return null;
    }

    public ArrayList<Product> findByWishList(@NotNull WishList wishlist){
        ArrayList<Product> productlist = null;
        if(wishlist.getId() != -1) {
            productlist = new ArrayList<Product>();
            String query1 = "SELECT * FROM Products p WHERE p.productID IN (SELECT productID FROM Wishlist_has_Products WHERE wishlistID == ?)";
            try (PreparedStatement q1 = this.connect.prepareStatement(query1)) {
                q1.setInt(1, wishlist.getId());
                ResultSet products = q1.executeQuery();

                while (products.next()) {
                    int productID = products.getInt("productID");
                    Product product = new Product( productID);
                    product.name = products.getString("name");
                    product.description = products.getString("description");
                    product.link = products.getString("link");
                    product.quantity = products.getInt("quantity");
                    product.purchased = products.getBoolean("purchased");
                    product.position = products.getInt("position");
                    product.wishlist = wishlist;
                    productlist.add(product);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return productlist;
    }

}
