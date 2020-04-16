package be.uclouvain.lsinf1225.groupeL11.wishlist;

import org.junit.Test;

import static org.junit.Assert.*;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUserDao {
    @Test
    public void addition_isCorrect() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}