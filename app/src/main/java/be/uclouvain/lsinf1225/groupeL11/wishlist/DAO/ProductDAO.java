package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.view.accessibility.AccessibilityRecord;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.DAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ProductDAO extends DAO<Product>{

    public ProductDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(Product product) {
        String query = "INSERT INTO Products (name, description, link) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setString(3, product.getLink());
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
    public boolean update(Product product) {
        String query = "UPDATE User " +
                "SET (name, description, link) = " +
                "(?, ?, ?) WHERE productID == ?";
        if(product.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setString(1, product.getName());
                pstmt.setString(2, product.getDescription());
                pstmt.setString(3, product.getLink());
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
    public Product find(String name) {
        Product product = null;
        try{
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Product WHERE name == \"" + name + "\"");
            if(result != null)
                product = new Product(
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("link"),
                        0,
                        null,
                        0,
                        null
                );
            product.setId(result.getInt("productID"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return product;
    }

    public ArrayList<Product> findByWishList(WishList wishlist){
        ArrayList<Product> productlist = null;
        if(wishlist.getId() != -1) {
            productlist = new ArrayList<Product>();
            String query1 = "SELECT * FROM Products p WHERE p.productID IN (SELECT productID FROM Wishlist_has_Products WHERE wishlistID == ?)";
            String query2 = "SELECT * FROM Wishlist_has_Products WHERE wishlistID == ? AND productID == ?";
            try (PreparedStatement q1 = this.connect.prepareStatement(query1)) {
                q1.setInt(1, wishlist.getId());
                ResultSet products = q1.executeQuery();

                while (products.next()) {
                    int productID = products.getInt("productID");
                    PreparedStatement q2 = this.connect.prepareStatement(query2);
                    q2.setInt(1, wishlist.getId());
                    q2.setInt(2, productID);
                    ResultSet whp_info = q2.executeQuery();

                    Product product = new Product(
                            products.getString("name"),
                            products.getString("description"),
                            products.getString("link"),
                            whp_info.getInt("position"),
                            whp_info.getString("shipping"),
                            whp_info.getInt("quantity"),
                            wishlist
                    );

                    product.setId(productID);
                    productlist.add(product);
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return productlist;
    }
}
