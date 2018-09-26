import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

public class BigTwoClient implements CardGame, NetworkGame {
	
	public BigTwoClient() {
		playerList=new ArrayList<CardGamePlayer>();
		handsOnTable=new ArrayList<Hand>();
		CardGamePlayer p1 = new CardGamePlayer();	//Creating 4 players
		CardGamePlayer p2 = new CardGamePlayer();
		CardGamePlayer p3 = new CardGamePlayer();
		CardGamePlayer p4 = new CardGamePlayer();
		playerList.add(p1);							//Adding the players to the PlayerList
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);
		table = new BigTwoTable(this);				//Making a new BigTwoTable
		table.repaint();
		playerName = JOptionPane.showInputDialog("Input your name: ");
		if(playerName.length()==0) {
			playerName="MR.PLAYER";
		}
		makeConnection();
		table.repaint();
		

	}
	
	private int numOfPlayers;
	private Deck deck= new BigTwoDeck();
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID=-1;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int currentIdx ;
	private BigTwoTable table;
	
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public Deck getDeck() {
		return deck;
	}
	public ArrayList<CardGamePlayer> getPlayerList(){
		return playerList;
	}
	public ArrayList<Hand> getHandsOnTable(){
		return handsOnTable;
	}
	public int getCurrentIdx() {
		return currentIdx;
	}
	
	
	private Card D3;
	public void start(Deck deck) {
		this.deck=deck;
		handsOnTable.clear();
		for (int i = 0; i < numOfPlayers; i++) {
			playerList.get(i).removeAllCards();
		}

		for(int i=0; i<4; i++)
		{
			for(int j=0; j<13; j++) {
				this.playerList.get(i).addCard(deck.getCard(13*i+j));			//Distributing BigTwoCards to each player
			}
			this.playerList.get(i).sortCardsInHand();						//Sorting them 
		}
		D3= new BigTwoCard(0,2);											//Card with Suit=Diamond & Rank=3
		for (int i=0; i<4; i++) {
			if (this.getPlayerList().get(i).getCardsInHand().contains(D3)) {		//Checking which player has the Card D3
				currentIdx=i;
				table.setActivePlayer(i);
			}
		}
		
		table.repaint();
		
		table.printMsg("Player " + playerList.get(currentIdx).getName() +"'s turn: \n");
		
	}
	public void makeMove(int playerID, int[] cardIdx) {
	
			sendMessage(new CardGameMessage(6,playerID, cardIdx));
		
	}
	
	
	private int lastPlayer=-1;
	public int getLast() {return lastPlayer;}
	private boolean isNotaMove=false;
	
	public void checkMove(int playerID, int[] cardIdx) {
		
		
		
		if(handsOnTable.size()==0) {					//Checking the 1st Turn
			table.setActivePlayer(currentIdx);
			if(!isNotaMove)	{									//Checking if Not a Legal move is employed or not
				}						//Printing screen
			int [] uselect=cardIdx;			//Retrieving the selected cards by the player
			if(uselect==null) {									//Checking if its pass or not
				table.printMsg("Not a legal move!!!\n");
				isNotaMove=true;
											//Make it do Turn 1 again
			}else {												//If players select something 
				CardList st=this.getPlayerList().get(currentIdx).play(uselect);		//Making a Cardlist of the selected cards
				CardGamePlayer us = this.getPlayerList().get(currentIdx);				//Retrieving the Player
				Hand stHand = composeHand(us, st);				//Making a Hand
				if(stHand==null||!st.contains(D3)) {								//Check if played hand is valid
					table.printMsg("Not a legal move!!!\n");
					isNotaMove=true;
												//Make it do Turn 1 again
				}
				else {
					
				this.handsOnTable.add(stHand);					//Appending to HandsOnTable list
				isNotaMove=false;							
				lastPlayer=currentIdx;							//Setting this as the last player who didn't pass
				playerList.get(currentIdx).removeCards(st);		//Removing the Hand from the Player's list of Cards
				table.printMsg("{"+ stHand.getType()+"} \n");
				print(st,true,false);										//Print the move
				this.currentIdx=(this.currentIdx+1)%4;			//Changing Current Index
				table.printMsg("Player " + playerList.get(currentIdx).getName() +"'s turn: \n");

				}
			}
			
		}
		
		else if(handsOnTable.size()>0) {//Checking if its not the 1st turn
			int [] uselect=cardIdx;							//Retrieving the selected cards by the player.
			if(uselect==null) {								//Checking if its pass or not
				if(lastPlayer==currentIdx) {					//Checking if this he is the one to which everyone passed
					table.printMsg("Not a legal move!!!\n");		//Printing Accordingly
					isNotaMove=true;
					
				}
				else {
					table.printMsg("{pass}\n");			
					isNotaMove=false;
					this.currentIdx=(this.currentIdx+1)%4;			//Changing current idx on passing
					table.printMsg("Player " + playerList.get(currentIdx).getName() +"'s turn: \n");

				}
					
			}else {					//If players select something 
				CardList st = this.getPlayerList().get(currentIdx).play(uselect);		//Making a Cardlist of the selected cards
				
				Hand stHand = composeHand(this.getPlayerList().get(currentIdx), st);		//Making a Hand
				if(stHand==null) {									//Checking  if Hand is Valid
					table.printMsg("Not a legal move!!!\n");		//Printing
					isNotaMove=true;
				}else {	//If Hand is Valid
					if(stHand.beats(getHandsOnTable().get(getHandsOnTable().size()-1))||lastPlayer==currentIdx) {//Checking if it beats the card on table
						this.handsOnTable.add(stHand);	// Appending it the the list of Cards on Table
						isNotaMove=false;
						lastPlayer=currentIdx;			//Setting this as the last player who didn't pass
						
						playerList.get(currentIdx).removeCards(st);		//Removing the Hand from the Player's list of Card
						table.printMsg("{"+ stHand.getType()+"} \n");	//Printing Selction
						print(st,true,false);
						this.currentIdx=(this.currentIdx+1)%4;				//Changing the current index to next player. 
						table.printMsg("Player " + playerList.get(currentIdx).getName() +"'s turn: \n");

					}else {
						table.printMsg("Not a legal move!!!\n");		//Printing counter part.
						isNotaMove=true;
					}
				}
			}
		}
		if (endOfGame() == true) {
			String s="";
			table.printMsg("Game Ends\n");
			for(int i=0; i<4; i++) {
				if(this.getPlayerList().get(i).getNumOfCards()==0) {		//Checking which player has 0 Cards
					table.printMsg(playerList.get(i).getName()+" wins the game.\n");			//Printing the result accordingly 
					s+=playerList.get(i).getName()+" wins the game.\n";
				}
				else {
					table.printMsg( playerList.get(i).getName() + " has " + this.getPlayerList().get(i).getNumOfCards() + " cards in hand.\n");
					s+=playerList.get(i).getName() + " has " + this.getPlayerList().get(i).getNumOfCards() + " cards in hand.\n";
				}
			}
			
			 int rst=JOptionPane.showConfirmDialog(null, s,"Game Results" ,JOptionPane.OK_CANCEL_OPTION);
			 if(rst==JOptionPane.OK_OPTION) {
				 sendMessage(new CardGameMessage(CardGameMessage.READY,-1,null));
				 
			 }else
				 System.exit(0);
			
			table.disable();
		}
	
	
}void print(CardList cards,boolean printFront,boolean printIndex) {		//A function to print cards on the Text Area of the Frame  
	if (cards.size() > 0) {
		for (int i = 0; i < cards.size(); i++) {
			String string = "";
			if (printIndex) {
				string = i + " ";
			}
			if (printFront) {
				string = string + "[" + cards.getCard(i) + "]";
			} else {
				string = string + "[  ]";
			}
			if (i % 13 != 0) {
				string = " " + string;
			}
			table.printMsg(string);
			if (i % 13 == 12 || i == cards.size() - 1) {
				table.printMsg("\n");
			}
		}
	} else {
		table.printMsg("[Empty]\n");
	}
}

	
	
	
	
	
	
	
	public static void main(String args[]){
		BigTwoClient game = new BigTwoClient();
		
	}
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Hand nSingle=new Single(player,cards);					//Creating new objects of all the SubClasses of Hand.
		Hand nPair=new Pair(player,cards);
		Hand nQuad=new Quad(player,cards);
		Hand nStraightFlush=new StraightFlush(player,cards);
		Hand nStraight=new Straight(player,cards);
		Hand nTriple=new Triple(player,cards);
		Hand nFlush=new Flush(player,cards);
		Hand nFullHouse=new FullHouse(player,cards);
		if(nSingle.isValid())									//Checking which SubClass it is
			return nSingle;										//Then returning the Specific Hand. 
	
		if(nPair.isValid())
			return nPair;
		
		if(nTriple.isValid())
			return nTriple;

		if(nStraightFlush.isValid())
			return nStraightFlush;
		
		if(nQuad.isValid())
			return nQuad;
		
		if(nFullHouse.isValid())
			return nFullHouse;
		
		if(nFlush.isValid())
			return nFlush;
		
		if(nStraight.isValid())
			return nStraight;
		
		return null;											//If not at all a valid hand then returning null. 
		
	}
	
	public int getPlayerID() {
		return playerID;
	}
	
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		playerList.get(playerID).setName(playerName);
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP=serverIP;
	}
	public int getServerPort() {
		return serverPort;
	}
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort=serverPort;
		
	}
	public void makeConnection() {
		setServerIP("127.0.0.1");
		setServerPort(2396);
		try {
			sock = new Socket(this.serverIP, this.serverPort);
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		Thread thread = new Thread (new ServerHandler());
		thread.start();
		sendMessage(new CardGameMessage(1,-1,playerName));
		sendMessage(new CardGameMessage(4,-1,null));
		table.repaint();
		
		
		
		
	}
	public class ServerHandler implements Runnable{

		@Override
		public void run() {
			
			try{
				while (true) {														
					parseMessage((CardGameMessage) ois.readObject());
					
				}		
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			table.repaint();
		}
		
	}
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception e) {
			e.getStackTrace();	
		}
	}
	public synchronized void parseMessage(GameMessage message) {
		
		
		if(message.getType()==CardGameMessage.PLAYER_LIST) {
			setPlayerID(message.getPlayerID());
			for (int i = 0; i < 4; i++) {
				if (((String[])message.getData())[i] != null) {
					this.playerList.get(i).setName(((String[])message.getData())[i]);
					
				}
			}
			this.table.repaint();
		}else if(message.getType()==CardGameMessage.JOIN) {
			
			playerList.get(message.getPlayerID()).setName((String) message.getData());
			numOfPlayers++;
			table.repaint();
			table.printMsg( playerList.get(message.getPlayerID()).getName() + " joined the game!\n");
			
		}else if(message.getType()==CardGameMessage.FULL) {
			playerID=-1;
			table.printMsg("Server is FULL, Player cannot Join the Game");
			table.repaint();
			
		}else if (message.getType()==CardGameMessage.QUIT) {
			table.printMsg( playerList.get(message.getPlayerID()).getName() + " quit the game.\n");
			playerList.get(message.getPlayerID()).setName("");
			numOfPlayers--;
			if(!endOfGame()) {
				table.disable();
				
				sendMessage(new CardGameMessage(4,-1,null));
				
				
				getPlayerList().get(0).removeAllCards();	//removing cards from players
				getPlayerList().get(1).removeAllCards();
				getPlayerList().get(2).removeAllCards();
				getPlayerList().get(3).removeAllCards();
				getDeck().removeAllCards();
			}
			table.repaint();
		}else if (message.getType()==CardGameMessage.READY) {
			table.printMsg(playerList.get(message.getPlayerID()).getName() + " is READY!!!\n" );
			handsOnTable=new ArrayList<Hand>();
			table.repaint();
		}else if(message.getType()==CardGameMessage.START){
			table.reset();
			getPlayerList().get(0).removeAllCards();	//removing cards from players
			getPlayerList().get(1).removeAllCards();
			getPlayerList().get(2).removeAllCards();
			getPlayerList().get(3).removeAllCards();
			getDeck().removeAllCards();
			start((BigTwoDeck)message.getData());
			table.enable();
			table.repaint();
		}else if(message.getType()==CardGameMessage.MOVE) {
			if(this.currentIdx==message.getPlayerID())
				checkMove(message.getPlayerID(),(int[])message.getData());
			else
				table.printMsg("Not the Legal Player\n");
			table.repaint();
		}else if(message.getType()==CardGameMessage.MSG) {
			table.printChat((String)message.getData()+"\n");
		}
	}
	
	@Override
	public boolean endOfGame() {
		for (int i = 0; i < 4; i++)
			if (this.getPlayerList().get(i).getNumOfCards() == 0)
				return true;
		return false;
	}
}
