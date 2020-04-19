package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.InterestDAO;

public class Interest {
    private int id=-1;
    private String interestname;

    public Interest(String name) {
        this.interestname = name;
    }

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getInterestName() {return this.interestname;}

}
