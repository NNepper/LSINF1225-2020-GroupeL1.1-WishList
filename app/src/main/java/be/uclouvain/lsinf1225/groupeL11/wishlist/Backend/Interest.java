package be.uclouvain.lsinf1225.groupeL11.wishlist.Backend;

public class Interest {
    private String interestName;
    private int interestRating;

    public Interest(String interestName, int interestRating) {
        this.interestName = interestName;
        this.interestRating = interestRating;
    }

    public String getInterestName() {
        return interestName;
    }

    public int getInterestRating() {
        return interestRating;
    }
}
