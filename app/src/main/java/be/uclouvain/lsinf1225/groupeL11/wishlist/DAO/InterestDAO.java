package be.uclouvain.lsinf1225.groupeL11.wishlist.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.uclouvain.lsinf1225.groupeL11.wishlist.Backend.Interest;

public class InterestDAO extends DAO<Interest>{

    public InterestDAO(Connection conn){
        super(conn);
    }

    @Override
    public boolean create(Interest interest){
        String query = "INSERT INTO Interests (name) VALUES (?)";
        try(PreparedStatement pstmt = this.connect.prepareStatement(query)){
            pstmt.setString(1, interest.getInterestName());
            pstmt.execute();
            ResultSet result = this.connect.createStatement().executeQuery("select last_insert_rowid()");
            interest.setId(result.getInt("last_insert_rowid()"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Interest interest){
        if(interest.getId() != -1) {
            String query = "DELETE FROM Interests WHERE interestID == ?";
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setInt(1, interest.getId());
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
    public boolean update(Interest interest){
        String query = "UPDATE Interests SET (name) = (?) WHERE interestID == ?";
        if(interest.getId() != -1) {
            try (PreparedStatement pstmt = this.connect.prepareStatement(query)) {
                pstmt.setString(1, interest.getInterestName());
                pstmt.setInt(2, interest.getId());
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
    public Interest find(String interestname) {
        Interest interest = null;
        try{
            ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM Interests WHERE name == \"" + interestname + "\"");
            if(result != null) {
                interest = new Interest(
                        result.getString("interestname")
                );
                interest.setId(result.getInt("userID"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return interest;
    }
}
