import java.io.Serializable;

public class Card  implements Serializable{
    private String suit;
    private int rank;
    
    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    public String getSuit() {
        return suit;
    }
    
    public int getRank() {
        return rank;
    }
    
    @Override
    public String toString() {
        String strRank;
        if (rank == 1) {
            strRank = "Ace";
        } else if (rank == 11) {
            strRank = "Jack";
        } else if (rank == 12) {
            strRank = "Queen";
        } else if (rank == 13) {
            strRank = "King";
        } else {
            strRank = Integer.toString(rank);
        }
        return strRank + " of " + suit;
    }
}
