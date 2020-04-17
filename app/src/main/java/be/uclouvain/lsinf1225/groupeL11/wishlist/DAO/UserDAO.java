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
        String query = "INSERT INTO User (firstname, lastname, username, email, password, address, fav_color, shoe_size)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";
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
        String query = " DELETE FROM User WHERE userID == ?";
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
        String query = "UPDATE User " +
            "SET (firstname, lastname, username, email, password, address, fav_color, shoe_size) = " +
                "(?, ?, ?, ?, ?, ?, ?, ?) WHERE userID == ?";
        if(user.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setString(1, user.getFirstname());
                pstmt.setString(2, user.getLastname());
                pstmt.setString(3, user.getUsername());
                pstmt.setString(4, user.getEmail());
                pstmt.setString(5, user.getPassword());
                pstmt.setString(6, user.getAddress());
                pstmt.setString(7, user.getColor());
                pstmt.setInt(8, user.getShoesSize());
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
                user = new User(
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
                user.setId(result.getInt("userID"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
 }
