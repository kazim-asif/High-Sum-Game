import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameModule implements Serializable {
	
	private Deck deck;
    private Player player;
    private Player dealer;
    private int pot;
    private int currentBet;
    private boolean gameOver;
    
    boolean shouldcontinue=true;
    
    public static Scanner input = new Scanner(System.in);
    
    public GameModule() {
        deck = new Deck();
//        player = new Player("Ice Peak","player",100);
        dealer = new Player("Dealer", "dealer",100);
        pot = 0;
        currentBet = 0;
        gameOver = false;
    }
    
    
    public void setPlayer(Player p) {
		player=new Player(p.getLoginName(),p.getHashPassword(),p.getChips());
	}
    

	private void dealCards() {
        for (int i = 0; i < 2; i++) {
            player.addToHand(deck.dealCard());
            dealer.addToHand(deck.dealCard());
        }
    }
    
    private void makepass(int num) {
    	for (int i = 0; i < num; i++) {
    		player.addToHand(deck.dealCard());
            dealer.addToHand(deck.dealCard());
    	}
    }

    public void start() {
    	
    	
    	gameOver=false;
    	
        System.out.println("\n\n\nStarting a new game of High Sum!\n");
        System.out.println(player.getLoginName()+" you have "+player.getCHips()+" chips\n"
        		+ "------------------------------------\n"
        		+ "Game Starts - Dealer shuffles deck.");
        deck.shuffle(); // deck is shuffled
        
        makepass(2);
//        dealCards();
        
        System.out.println("--------------------\nRound 1\n--------------------");
        
        printCard(1);
        
        determineBet(1);
        
        int round=2;
        while (!gameOver && round<=4) {
            System.out.println("-------------\nRound " + round+"\n-------------\n");
            
            playRound(round);
            
            if(round==4) {
            	determineWinner();
            }
            
            round+=1;
        }
    }
    
    void printCard( int round){
    	
    	System.out.print("Dealer's hand: " + "<Hidden Card>, ");
    	for (int i=1; i<=round; i++) {
            System.out.print( dealer.getCardsonHand().get(i) + ", ");
    	}
        System.out.print("\n"+player.getLoginName()+"'s hand: " + player.getCardsonHand().get(0) +", " );
        for (int i=1; i<=round; i++) {
            System.out.print( player.getCardsonHand().get(i) + ", ");
    	}
        
        System.out.println("\nScore : "+ player.getTotalCardsValue());
    	
    	
    }
    
    
    private void determineWinner() {
    	int playerScore = player.getTotalCardsValue();
    	int dealerScore = dealer.getTotalCardsValue();
    	
    	System.out.println("\n-------------------------------\nGame End\n-------------------------------\n");
    	System.out.println("Dealer cards");
    	for( int i=0; i<dealer.getCardsonHand().size(); i++ ) {
    		System.out.print( dealer.getCardsonHand().get(i)+"  " );
    	}
    	System.out.println("\n-------- Score : "+ dealerScore);
    	
    	System.out.println("\n"+player.getLoginName()+" cards");
    	for( int i=0; i<player.getCardsonHand().size(); i++ ) {
    		System.out.print( player.getCardsonHand().get(i)+"  " );
    	}
    	System.out.println("\n-------- Score : "+ playerScore+"\n");
    	
    	int betOnTable = 2*player.getBet();
    	
    	if(playerScore > dealerScore) {
    		player.setChips(betOnTable + player.getCHips());
    		System.out.println(player.getLoginName()+" wins\nYou have "+player.getCHips()+" chips");
    	}
    	else if(playerScore < dealerScore) {
    		dealer.setChips(betOnTable + dealer.getCHips());
    		System.out.println("Dealer wins\n"+player.getLoginName()+" You have "+player.getCHips()+" chips");
    	}
    }
    
    private void playRound(int round) {
    	makepass(1);
    	printCard(round);
    	determineBet(round);
		
	}

	public void determineBet(int round) {
    	if(player.getCardsonHand().get(round).getRank() > dealer.getCardsonHand().get(round).getRank() ) {
    		System.out.print("Player call, ");
    		
    		while(true) {
    			if( round>=2 ) {
    				String call;
    				System.out.println("[C]all or [Q]uit ?");
    				call= input.next();
    				
    				if(call.equalsIgnoreCase("q")) {
    					System.out.println(player.getLoginName()+" has quit the game");
    					
    					dealer.setChips(2*dealer.getBet() + dealer.getCHips());
    					//	System.out.println("Dealer you have "+dealer.getChip()+" chips");
    					System.out.println(player.getLoginName()+" you have "+player.getCHips()+" chips");
    					System.out.println("Game ended");
    					gameOver=true;
    					shouldcontinue=false;
    					return;
    					//System.exit(0);
//    					break;
    				}
    				
    				if(!call.equalsIgnoreCase("c")) {
    					continue;
    				}
    			}
    			System.out.print("Enter your bet amount: ");
                int bet = input.nextInt();
                if(bet > player.getCHips() ) {
                	System.out.println("Bet can't be greater than available chips");
                }
                else if (bet<10) {
                	System.out.println("Can't be less than 10");
                }
                else if (bet>dealer.getCHips()) {
                	System.out.println("Dealer does'nt have enough chips");
                }
                else {
                	System.out.println("state bet : "+bet);
                	player.placeBet(bet);
                	dealer.placeBet(bet);
                	player.setChips(player.getCHips()-bet);
                	dealer.setChips(dealer.getCHips()-bet);
                	System.out.println(player.getLoginName()+" you are left with "+player.getCHips()+" chips");
                	System.out.println("Bet on table : "+(2*player.getBet()));
                	break;
                }
    		}
    	}
    	else if(dealer.getCardsonHand().get(round).getRank() >= player.getCardsonHand().get(round).getRank() ) {
    		
    		int bet =10;
    		System.out.println("Dealer auto bet of : "+bet);
    		
    		if( round>=2 ) {
				String call;
				System.out.println("Do you want to follow? [Y/N]:");
				call= input.next();
				
				if(call.equalsIgnoreCase("n")) {
					System.out.println(player.getLoginName()+" has quit the game");
					
					dealer.setChips(2*dealer.getBet() + dealer.getCHips());
				//	System.out.println("Dealer you have "+dealer.getChip()+" chips");
					System.out.println(player.getLoginName()+" you have "+player.getCHips()+" chips");

					System.out.println("Game ended");
					
					shouldcontinue=false;
					gameOver=true;
					
					return;
					//System.exit(0);
				}
			}
    		
            	player.setChips(player.getCHips()-bet);
            	dealer.setChips(dealer.getCHips()-bet);
            	player.placeBet(bet);
            	dealer.placeBet(bet);
            	
            	System.out.println(player.getLoginName()+" you are left with "+player.getCHips()+" chips");
            	System.out.println("Bet on table : " + (2*dealer.getBet()) );
              
    	}
    }
	
	
	

public static void main(String[] args) {
		
		GameModule game = new GameModule();
		Admin admin = new Admin();
		boolean login=false;
		ArrayList<Player> players = admin.getPlayers();
		int foundat=0;
		
    	while(!login) {
    		System.out.print("Enter Login Name > ");
        	String name = input.nextLine();
        	
        	System.out.print("Enter Password > ");
        	String playerPassword = input.nextLine();
            playerPassword = Utility.getHash(playerPassword);
    	    
        	for(Player p: players) {
        		if(p.getLoginName().equals(name)) {
        			if(p.getHashPassword().equals(playerPassword)) {
        				System.out.println("Password matched! \n");
//        				game.setPlayer(p);
        				game.player=p;
        				login=true;
        				break;
        			}
        		}
        		foundat++;
        	}
        	if(!login) {
        		foundat=0;
        		System.out.println("\nWrong credentials!\n");
        	}
        	
    	}
		
		
		while(true) {
		
			game.start();
			System.out.println("Next game (Y/N)");
			String typed = "";
			while(!typed.equalsIgnoreCase("y") && !typed.equalsIgnoreCase("n") )
				typed = input.next();
			if(typed.equalsIgnoreCase("n")) {
				
				players.set(foundat, game.player);
				admin.setPlayers(players);
				admin.displayMenu(-1);
//				
//				int i=-1;
//				for(Player p: admin.getPlayers()) {
//					i++;
//	        		if(p.getLoginName().equals(game.player.getLoginName())) {
//	        			if(p.getHashPassword().equals(game.player.getHashPassword())) {
//	        				break;
//	        			}
//	        		}
//	        		
//	        	}
//				if(i>-1) {
//					admin.getPlayers().set(i, game.player);
//					admin.savePlayersToBin();
//				}
				break;
			}
		
		}
		
		
	}
	
	
//	public static void main(String[] args) {
//		
//		GameModule game = new GameModule();
//		Admin admin = new Admin();
//		
//		
//		while(true) {
//			String in = input.nextLine();
//			System.out.println("Want admin menu? press 1\nWant play game press 2\nWant exit press 0");
//			  in = input.nextLine();
//			if(in.equalsIgnoreCase("1")) {
//				System.out.println("Enter password");
//				String pas=input.nextLine();
//				if(!admin.checkPassword(pas)) {
//					System.out.println("Wrong password!");
//					continue;
//				}
//				admin.displayMenu();
//			}
//			else if(in.equalsIgnoreCase("2")) {
//				
//				boolean login=false;
//				
//		    	System.out.print("Enter Login Name > ");
//		    	String name = input.nextLine();
//		    	
//		    	System.out.print("Enter Password > ");
//		    	String playerPassword = input.nextLine();
//		        playerPassword = Utility.getHash(playerPassword);
//			    
//		    	for(Player p: admin.getPlayers()) {
//		    		if(p.getLoginName().equals(name)) {
//		    			if(p.getHashPassword().equals(playerPassword)) {
//		    				System.out.println("Password matched! \n");
////		    				game.setPlayer(p);
//		    				game.player=p;
//		    				login=true;
//		    				break;
//		    			}
//		    		}
//		    	}
//				if(login) {
//					game.start();
//					if(game.shouldcontinue) {
//						System.out.println("Next game (Y/N)");
//						String typed = "";
//						while(!typed.equalsIgnoreCase("y") && !typed.equalsIgnoreCase("n") )
//							typed = input.next();
//						if(typed.equalsIgnoreCase("n")) {
//							break;
//						}
//					}
////					else {
////						Player ptoupdate=null;int i=0;
////						for (Player p : admin.getPlayers()) {
////							if(p.getLoginName().equals(game.player.getLoginName())) {
////								ptoupdate=p;
////								
////								break;
////							}
////							i++;
////						}
////						if(ptoupdate!=null) {
////							admin.getPlayers().set(i, ptoupdate);
////						}
////						admin.savePlayersToBin();
////					}
//				}
//			}
//			else if(in.equals("0")) {
//				return;
//			}
//		
//		}
//		
//	}
	
}
