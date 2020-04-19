package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import android.view.accessibility.AccessibilityRecord;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.DAO;

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
                        result.getString("link")
                );
            product.setId(result.getInt("productID"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return product;
    }

    public static void setWeblink(String newWeblink) {
        //TODO
    }

    public static void setShipping(String newShipping) {
        //TODO
    }

    public static void setPosition(int position) {
        //TODO
    }

    public static void setQuantity(int quantity) {
        //TODO
    }

    public static String getProductName(Product product) {
        //TODO
        return null;
    }

    public static String getProductDescription(Product product) {
        //TODO
        return null;
    }

    public static String getWeblink(Product product) {
        //TODO
        return null;
    }

    public static String getProductPicture(Product product) {
        //TODO
        return null;
    }

    public static String getShipping(Product product) {
        //TODO
        return null;
    }

    public static int getPosition(Product product) {
        //TODO
        return 1;
    }

    public static int getQuantity(Product product) {
        //TODO
        return 1;
    }

}
