import java.io.Serializable;
import java.util.*;

public class Player extends user{

	private int chips;
	protected ArrayList<Card> cardsOnHand;
	protected int bet;

	
	public Player(String loginName, String password, int chips){
		super(loginName, password);
		this.chips = chips;
		this.cardsOnHand = new ArrayList<Card>();
	}
	
	
	public int getChips() {
		return chips;
	}
	public void setChips(int chips) {
		this.chips = chips;
	}
	public ArrayList<Card> getCardsOnHand() {
		return cardsOnHand;
	}
	public void setCardsOnHand(ArrayList<Card> cardsOnHand) {
		this.cardsOnHand = cardsOnHand;
	}
	public int getBet() {
		return bet;
	}
	public void setBet(int bet) {
		this.bet = bet;
	}
	
	public int getCHips(){
		return this.chips;
	}
	public void addChips(int amount){
		this.chips+=amount;
	}
	public void deductChips(int amount){
		this.chips-=amount;
	}
	public void addCard(Card card){
		this.cardsOnHand.add(card);
	}
	public ArrayList<Card> getCardsonHand(){
		return this.cardsOnHand;
	}
	public void showCardsonHand(){
		System.out.println(getLoginName());
		for(Card card:cardsOnHand){
			System.out.print(card+" ");
		}
		System.out.println();
	}
	public void showTotalCardsValue(){
		System.out.println("Value:"+getTotalCardsValue());
	}
	public int getTotalCardsValue(){
		int total =0;
		for(Card card: this.cardsOnHand){
			total+=card.getRank();
		}
		return total;
	}
	public void display(){
		super.display();
		System.out.println(this.chips);
	}

	public static void main(String[]args){
		Player player = new Player("IcePeak","A",100);
				System.out.println(player.getCHips());
		player.deductChips(10);
		
	}
	public void addToHand(Card card) {
		// TODO Auto-generated method stub
		addCard(card);
	}
	public void placeBet(int amount) {
        bet += amount;
    }
	
	
	
}