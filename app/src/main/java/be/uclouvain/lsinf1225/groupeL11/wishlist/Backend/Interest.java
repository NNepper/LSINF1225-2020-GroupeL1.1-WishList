package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

import be.uclouvain.lsinf1225.groupeL11.wishlist.DAO.InterestDAO;

public class Interest {

    public Interest() {
        //TODO
    }

    public String getInterestName(Interest interest) { return InterestDAO.getInterestName(interest); }

    public int getInterestRating(Interest interest) { return InterestDAO.getInterestRating(interest); }

    public void setInterestRating(int newRating) { InterestDAO.setInterestRating(newRating); }
}
