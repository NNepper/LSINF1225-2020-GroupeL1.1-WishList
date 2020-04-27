package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class UserDAO extends DAO<User>{

    public UserDAO(Connection conn){
        super(conn);
    }

    @Override
    public boolean create(User user){
        String query = "INSERT INTO User (firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setString(1, user.firstname);
            pstmt.setString(2, user.lastname);
            pstmt.setString(3, user.username);
            pstmt.setString(4, user.email);
            pstmt.setString(5, user.password);
            pstmt.setString(6, user.address);
            pstmt.setString(7, user.color);
            pstmt.setInt(8, user.shoeSize);
            pstmt.setString(9, user.trouserSize);
            pstmt.setString(10, user.tshirtSize);
            pstmt.execute();
            ResultSet result = this.connect.createStatement().executeQuery("select last_insert_rowid()");
            user.setId(result.getInt("last_insert_rowid()"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User user){
        if(user.getId() != -1) {
            String query = " DELETE FROM User WHERE userID == ?";
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setInt(1, user.getId());
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
    public boolean update(User user) {
        String query = "UPDATE User " +
            "SET (firstname, lastname, username, email, password, address, fav_color, shoe_size) = " +
                "(?, ?, ?, ?, ?, ?, ?, ?) WHERE userID == ?";
        if(user.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setString(1, user.firstname);
                pstmt.setString(2, user.lastname);
                pstmt.setString(3, user.username);
                pstmt.setString(4, user.email);
                pstmt.setString(5, user.password);
                pstmt.setString(6, user.address);
                pstmt.setString(7, user.color);
                pstmt.setInt(8, user.shoeSize);
                pstmt.setInt(9, user.getId());
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
    public User find(String email){
        User user = null;
        try{
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM User WHERE email == \"" + email + "\"");
            if(result != null)
                user = new User(result.getInt("userID"));
                user.firstname = result.getString("firstname");
                user.lastname = result.getString("lastname");
                user.username = result.getString("username");
                user.email = email;
                user.password = result.getString("password");
                user.color = result.getString("fav_color");
                user.shoeSize = result.getInt("shoe_size");
                user.trouserSize = result.getString("trouser_size");
                user.tshirtSize = result.getString("tshirt_size");
                user.tshirtSize = result.getString("address");
                user.setId(result.getInt("userID"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
 }
