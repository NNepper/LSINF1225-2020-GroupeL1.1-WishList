package be.uclouvain.lsinf1225.groupeL11.wishlist;

import org.junit.Test;

import static org.junit.Assert.*;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;

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
    public void test_create(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User(-1);
            new_user.firstname = "Tom";
            new_user.lastname = "Hanks";
            new_user.email = "tomhanks@gmail.com";
            new_user.password = "captainphillips";
            new_user.username = "thanks";
            new_user.color = "blue";
            new_user.tshirtSize = "small";
            new_user.trouserSize = "small";
            new_user.shoeSize = 42;
            new_user.address = "chez ta mere";
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
            User new_user = new User(-1);
            new_user.firstname = "Tom";
            new_user.lastname = "Hanks";
            new_user.email = "tomhanks@gmail.com";
            new_user.password = "captainphillips";
            new_user.username = "thanks";
            new_user.color = "blue";
            new_user.tshirtSize = "small";
            new_user.trouserSize = "small";
            new_user.shoeSize = 42;
            new_user.address = "chez ta mere";
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
            User new_user = new User(-1);
            new_user.firstname = "Tom";
            new_user.lastname = "Hanks";
            new_user.email = "tomhanks@gmail.com";
            new_user.password = "captainphillips";
            new_user.username = "thanks";
            new_user.color = "blue";
            new_user.tshirtSize = "small";
            new_user.trouserSize = "small";
            new_user.shoeSize = 42;
            new_user.address = "chez ta mere";
            dao.create(new_user);
            User found_user = dao.find(new_user.email);
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
            User new_user = new User(-1);
            new_user.firstname = "Tom";
            new_user.lastname = "Hanks";
            new_user.email = "tomhanks@gmail.com";
            new_user.password = "captainphillips";
            new_user.username = "thanks";
            new_user.color = "blue";
            new_user.tshirtSize = "small";
            new_user.trouserSize = "small";
            new_user.shoeSize = 42;
            new_user.address = "chez ta mere";
            dao.create(new_user);
            new_user.email = "newadress@gmail.com";
            dao.update(new_user);
            User found_user = dao.find(new_user.email);
            assertEquals(found_user.getId(), new_user.getId());
            dao.delete(new_user);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_find_non_exising_row(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User found_user = dao.find("non-existing-email@gmail.com");
            assertNull(found_user);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_update_non_existing_row(){
        String url = "jdbc:sqlite:db/bdd.sqlite";
        try(Connection conn = DriverManager.getConnection(url)){
            UserDAO dao = new UserDAO(conn);
            User new_user = new User(-1);
            new_user.firstname = "Tom";
            new_user.lastname = "Hanks";
            new_user.email = "tomhanks@gmail.com";
            new_user.password = "captainphillips";
            new_user.username = "thanks";
            new_user.color = "blue";
            new_user.tshirtSize = "small";
            new_user.trouserSize = "small";
            new_user.shoeSize = 42;
            new_user.address = "chez ta mere";
            boolean ret = dao.update(new_user);
            assertFalse(ret);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}