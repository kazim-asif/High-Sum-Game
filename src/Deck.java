import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable{
    private List<Card> cards;
    
    public Deck() {
        cards = new ArrayList<Card>();
        String[] suits = {"spades", "hearts", "clubs", "diamonds"};
        for (String suit : suits) {
            for (int i = 1; i <= 13; i++) {
                cards.add(new Card(suit, i));
            }
        }
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    public Card dealCard() {
        return cards.remove(0);
    }
}
