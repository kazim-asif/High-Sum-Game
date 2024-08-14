
import java.util.*;
import java.io.*;

public class Admin implements Serializable{
    private ArrayList<Player> players;
    private final String PLAYERS_FILENAME = "src\\players.bin";
    private final String ADMIN_FILENAME = "src\\admin.txt";
    private String adminPassword;

    public Admin() {
        players = new ArrayList<Player>();
        loadPlayers();
        loadAdmin();
    }

    
    
    public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}



	public String getPLAYERS_FILENAME() {
		return PLAYERS_FILENAME;
	}



	public String getADMIN_FILENAME() {
		return ADMIN_FILENAME;
	}



	public boolean checkPassword(String pass) {
    	 if(Utility.getHash(pass).equals(adminPassword)) {
    		 return true;
    	 }
    	return false;
    }
    private void loadPlayers() {
        try {
            FileInputStream file = new FileInputStream(PLAYERS_FILENAME);
            ObjectInputStream output = new ObjectInputStream(file);
            boolean endOfFile = false;
            while (!endOfFile) {
                try {
                    Player player = (Player) output.readObject();
                    players.add(player);
                } catch (EOFException ex) {
                    endOfFile = true;
                }
            }
            System.out.println("Players information loaded");
            output.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
        
    }

    public void savePlayersToBin() {
        try {
            FileOutputStream file = new FileOutputStream(PLAYERS_FILENAME);
            ObjectOutputStream opStream = new ObjectOutputStream(file);

            for (Player player : players) {
                opStream.writeObject(player);
                opStream.flush();
            }
            opStream.close();
        } catch (IOException ex) {
            System.out.println("Error saving players data");
            ex.printStackTrace();
        }
    }

    private void loadAdmin() {
        try {
            FileInputStream file = new FileInputStream(ADMIN_FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            adminPassword = reader.readLine();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Admin file not found");
        } catch (IOException ex) {
            System.out.println("Error reading admin data");
        }
    }

    private void saveAdmin() {
        try {
            FileOutputStream file = new FileOutputStream(ADMIN_FILENAME);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));
            writer.write(adminPassword);
            writer.newLine();
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error saving admin data");
        }
    }
   
    private void displayPlayers() {
        System.out.println("List of players:");
        for (Player player : players) {
            player.display();
        }
        System.out.println();
    }

    private void createNewPlayer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter player name: ");
        String playerName = input.nextLine();
        System.out.print("Enter player password: ");
        String playerPassword = input.nextLine();
        System.out.print("Enter starting chip amount: ");
        int chipAmount = input.nextInt();
        input.nextLine(); // consume new line character
        Player newPlayer = new Player(playerName, playerPassword, chipAmount);
        players.add(newPlayer);
        System.out.println("New player added:");
        newPlayer.display();
        System.out.println();
    }

    private void deletePlayer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter player name: ");
        String playerName = input.nextLine();
        Player playerToDelete = null;
        for (Player player : players) {
            if (player.getLoginName().equals(playerName)) {
                playerToDelete = player;
                break;
            }
        }
        if (playerToDelete == null) {
            System.out.println("Player not found");
        } else {
            players.remove(playerToDelete);
            System.out.println("Player deleted:");
            playerToDelete.display();
        }
        System.out.println();
    }

    private void issueChips() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter player name: ");
        String playerName = input.nextLine();
        Player playerFound = null;
        for (Player player : players) {
            if (player.getLoginName().equals(playerName)) {
            	playerFound = player;
                break;
            }
        }
        if (playerFound == null) {
            System.out.println("Player not found");
        } else {
        	System.out.print("Enter number of chips you want to issue");
            int x=input.nextInt();
            while(x<10) {
            	System.out.println("You can't issue less tha 10.Enter again");
            	x=input.nextInt();
            }
            playerFound.addChips(x);
            System.out.println(x+" Chips issued to player:");
            playerFound.display();
        }
        System.out.println();
        
        
        
    }


    public void resetPlayerPassword() {
    	
    	Scanner input = new Scanner(System.in);
    	
        System.out.print("Enter player name: ");
        String playerName = input.nextLine();
        Player playerFound = null;
        for (Player player : players) {
            if (player.getLoginName().equals(playerName)) {
            	playerFound = player;
                break;
            }
        }
        if (playerFound == null) {
            System.out.println("Player not found");
        } 
        else {
	    	System.out.println("Enter new password");
	    	String newPassword;
	    	newPassword=input.nextLine();
	    	
	        if (players.contains(playerFound)) {
	            playerFound.setHashPassword(newPassword);
	            System.out.println("Player password reset.");
	        } else {
	            System.out.println("Player not found.");
	        }
        }
    }

    public void changeAdminPassword() {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter current password");
    	String currentPassword;
    	currentPassword=input.nextLine();
    	
        if (Utility.getHash(currentPassword).equals(adminPassword)) {
        	System.out.println("Enter new password");
        	String newPassword;
        	newPassword=input.nextLine();
            adminPassword = Utility.getHash(newPassword);
            System.out.println("Admin password changed.\n");
        } else {
            System.out.println("Incorrect password.\n");
        }
    }

    public void logout() {
        System.out.println("Logged out.");
//        adminPassword="";
    }
    
    
    public void displayMenu(int flag) {
        Scanner input = new Scanner(System.in);
        boolean quit = false;

        if(flag==-1) {
        	savePlayersToBin();
        	return;
        }
        
        while(!quit) {
        
//        	System.out.println("\n-----------------------------------------All the info will be updated once you will logout-----------------------------------------\n");
        	
            System.out.println("Select an option:");
            System.out.println("1. Display all players");
            System.out.println("2. Create new player");
            System.out.println("3. Delete player");
            System.out.println("4. Issue chips to player");
            System.out.println("5. Reset player password");
            System.out.println("6. Change admin password");
            System.out.println("7. Logout");

            int choice = input.nextInt();
            input.nextLine(); // consume new line character

            switch (choice) {
                case 1:
                    displayPlayers();
                    break;
                case 2:
                    createNewPlayer();
                    savePlayersToBin();
                    break;
                case 3:
                    deletePlayer();
                    savePlayersToBin();
                    break;
                case 4:
                    issueChips();
                    savePlayersToBin();
                    break;
                case 5:
                    resetPlayerPassword();
                    savePlayersToBin();
                    break;
                case 6:
                    changeAdminPassword();
                    break;
                case 7:{
                	saveAdmin();
                	savePlayersToBin();
                    logout();
                    quit = true;
                    break;
                }
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
    }
    
    
    boolean Login(){
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("Enter password");
		String pas=input.nextLine();
		if(!checkPassword(pas)) {
			System.out.println("Wrong password!");
			return false;
		}
    	
		return true;
    	
    }
    
    public static void main(String[] args) {
		
    	Admin admin = new Admin();
    	
    	while(true)
    	 if(admin.Login())
    		 break;
    	
    	admin.displayMenu(0);
    	
    	
	}
    
    
    
}


