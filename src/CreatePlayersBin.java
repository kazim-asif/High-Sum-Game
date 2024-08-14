import java.io.*;
public class CreatePlayersBin {
	
	
public static void main(String[]args){
	Player player1 = new Player("Player1","Password1",100);
	Player player2 = new Player("Player2","Password2",200);
	Player player3 = new Player("Player3","Password3",300);
	
	try {
		FileOutputStream file = new FileOutputStream("players.bin");
		ObjectOutputStream opStream= new ObjectOutputStream(file);
		
		opStream.writeObject(player1);
		opStream.writeObject(player2);
		opStream.writeObject(player3);
		opStream.close();

	}catch(IOException ex){
		
	}
}
}
