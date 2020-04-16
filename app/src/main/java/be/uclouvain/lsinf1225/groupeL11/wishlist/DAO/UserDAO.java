package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;

public class UserDAO extends DAO<User>{

    public UserDAO(Connection conn){
        super(conn);
    }

    @Override
    public boolean create(User user){
        String query = "INSERT INTO " +
                "User(firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getLastname());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getColor());
            pstmt.setInt(8, user.getShoesSize());

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User user){
        String query = "DELETE FROM User WHERE User.userID == ?";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setInt(1, user.getId());
            pstmt.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE " +
            "User(firstname, lastname, username, email, password, address, fav_color, shoe_size, trouser_size, tshirt_size) = " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setString(1, user.getFirstname());
            pstmt.setString(2, user.getLastname());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getColor());
            pstmt.setInt(8, user.getShoesSize());
            pstmt.setString(10, user.getTshirtSize());
            pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public User find(String email){
        User user = null;
        String query = "SELECT * FROM User WHERE User.userID == ?";
        try{
            ResultSet result = this.connect.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            ).executeQuery("SELECT * FROM User WHERE User.email == " + email);
            if(result.first())
                user = new User(
                        result.getInt("userID"),
                        result.getString("firstname"),
                        result.getString("lastname"),
                        result.getString("username"),
                        email,
                        result.getString("password"),
                        result.getString("fav_color"),
                        result.getString("fav_color"),
                        result.getInt("shoe_size"),
                        result.getString("address")
                );
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
 }
