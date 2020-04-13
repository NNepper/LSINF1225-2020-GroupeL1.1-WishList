package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.InterestDAO;

public class Interest {

    public Interest() {
        //TODO
    }

    public String getInterestName(int interestID) { return InterestDAO.getInterestName(interestID); }

    public int getInterestRating(int interestID) { return InterestDAO.getInterestRating(interestID); }

    public void setInterestRating(int newRating,int interestID) { InterestDAO.setInterestRating(interestID, newRating); }
}
