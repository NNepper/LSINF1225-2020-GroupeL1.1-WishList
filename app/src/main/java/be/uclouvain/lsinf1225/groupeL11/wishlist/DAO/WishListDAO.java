package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.WishList;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Product;
import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.User;
import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.DAO;

public class WishListDAO extends DAO<WishList>{

    public WishListDAO(Connection conn) {
        super(conn);
    }

    @Override
    public boolean create(WishList list) {
        if(list.user != null && list.user.getId() != -1) {
            String add_to_wishlisttable = "INSERT INTO Wishlist (name, description) VALUES (?, ?)";
            String link_user_to_wishlist = "INSERT INTO User_has_Wishlist (userID, wishlistID) VALUES (?, ?)";
            try (
                    PreparedStatement query1 = this.connect.prepareStatement(add_to_wishlisttable);
                    PreparedStatement query2 = this.connect.prepareStatement(link_user_to_wishlist)
            ) {

                query1.setString(1, list.name);
                query1.setString(2, list.description);
                query1.execute();
                ResultSet result = this.connect.createStatement().executeQuery("select last_insert_rowid()");
                list.setId(result.getInt("last_insert_rowid()"));

                query2.setInt(1, list.user.getId());
                query2.setInt(2, list.getId());
                query2.execute();

                if(list.products != null){
                    ProductDAO p_dao = new ProductDAO(this.connect);
                    for (Product p: list.products){
                        p_dao.create(p);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(WishList list) {
        if(list.getId() != -1) {
            String query1 = " DELETE FROM Wishlist WHERE wishlistID == ?";
            String query2 = " DELETE FROM User_has_wishlist WHERE wishlistID == ?";
            try (PreparedStatement delete_wishlist = this.connect.prepareStatement(query1)) {
                delete_wishlist.setInt(1, list.getId());
                delete_wishlist.execute();
                PreparedStatement delete_link = this.connect.prepareStatement(query2);
                delete_link.setInt(1, list.getId());
                delete_link.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(WishList list) {
        String query = "UPDATE Wishlist SET (name, description) = (?, ?) WHERE wishlistID == ?";
        if(list.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setString(1, list.name);
                pstmt.setString(2, list.description);
                pstmt.setInt(3, list.getId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
            if(list.products != null) {
                //Update every product in list
                ProductDAO p_dao = new ProductDAO(this.connect);
                for (Product p : list.products) {
                    if (p_dao.update(p) == false)
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WishList find(String email) {
        return null;
    }

    public ArrayList<WishList> findByUser(User user) {
        ArrayList<WishList> a_list = null;
        if (user.getId() != -1){
            a_list = new ArrayList<WishList>();
            String query = "SELECT * FROM Wishlist w WHERE w.wishlistID IN (SELECT wishlistID FROM User_has_Wishlist WHERE userID == ?)";
            try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
                pstmt.setInt(1, user.getId());
                ResultSet result = pstmt.executeQuery();
                while(result.next()){
                    WishList list = new WishList(result.getInt("wishlistID"));
                    list.name = result.getString("name");
                    list.description = result.getString("description");
                    list.user = user;
                    a_list.add(list);
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return a_list;
    }


}
