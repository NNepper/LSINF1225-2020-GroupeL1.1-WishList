package be.uclouvain.lsinf1225.groupeL11.wishlist;

import org.junit.Test;

import static org.junit.Assert.*;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.UserDAO;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestUserDao {
    @Test
    public void test_create() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User("Tom", "Hanks", "tomhanks@gmail.com", "captainphillips", "thanks", "blue", "small", 42, "chez ta mere");
            boolean err = dao.create(new_user);
            assertTrue(err);
            dao.delete(new_user);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_delete() {
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User("Bruce", "Willis", "tbobruce@gmail.com", "diehard", "bwillis", "red", "medium", 43, "a cote de chez toi");
            dao.create(new_user);
            boolean err = dao.delete(new_user);
            assertTrue(err);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_find(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User("Bruce", "Willis", "tbobruce@gmail.com", "diehard", "bwillis", "red", "medium", 43, "a cote de chez toi");
            dao.create(new_user);
            User found_user = dao.find(new_user.getEmail());
            assertEquals(found_user.getId(), new_user.getId());
            dao.delete(new_user);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_update(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User("Bruce", "Willis", "tbobruce@gmail.com", "diehard", "bwillis", "red", "medium", 43, "a cote de chez toi");
            dao.create(new_user);
            new_user.setEmail("newadress@gmail.com");
            dao.update(new_user);
            User found_user = dao.find(new_user.getEmail());
            assertEquals(found_user.getId(), new_user.getId());
            dao.delete(new_user);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}