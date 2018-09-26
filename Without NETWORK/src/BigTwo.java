import java.util.ArrayList;
/**The BigTwo class is used to model a Big Two card game. 
 * It has private instance variables for storing a deck of cards, a list of players, a list of hands played on the table, an index of the current player, and a console for providing the user interface.
 * @author souravpadhiari
 *
 */
public class BigTwo implements CardGame {
	private Deck deck= new BigTwoDeck();
	private ArrayList<CardGamePlayer> playerList=new ArrayList<CardGamePlayer>();
	private ArrayList<Hand> handsOnTable;
	private int currentIdx;
	private BigTwoTable table;					

	/**
	 * The constructor for creating a Big Two card game. 
	 */
	public BigTwo() {
		CardGamePlayer p1 = new CardGamePlayer();	//Creating 4 players
		CardGamePlayer p2 = new CardGamePlayer();
		CardGamePlayer p3 = new CardGamePlayer();
		CardGamePlayer p4 = new CardGamePlayer();
		playerList.add(p1);							//Adding the players to the PlayerList
		playerList.add(p2);
		playerList.add(p3);
		playerList.add(p4);
		table = new BigTwoTable(this);				//Making a new BigTwoTable
	
		
	}
	
	/**A method for getting the number of players.
	 * @return Number of Players.
	 */
	public int getNumOfPlayers() {
		return 4;
	}
	
	/**The method for retrieving the deck of cards being used. 
	 * @return deck of cards.
	 */
	public Deck getDeck() {
		return deck;
	}
	/** The method for retrieving the list of players. 
	 * @return the list of players.
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return playerList;
	}
	/**The method for retrieving the list of hands played on the table.
	 * @return the list of hands on table.
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return handsOnTable;
	}
	/**The method for retrieving the index of the current player. 
	 * @return current index.
	 */
	public int getCurrentIdx() {
		return currentIdx;
	}
	private Card D3;
	/**The method for starting the game with shuffled deck of BigTwoCards.
	 * This method implements all the game logics.
	 * @param deck is the deck of BigTwoCards. 
	 */
	
	public void start(Deck deck) {
		handsOnTable = new ArrayList<Hand>();
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<13; j++) {
				this.playerList.get(i).addCard(deck.getCard(13*i+j));			//Distributing BigTwoCards to each player
			}
			this.playerList.get(i).sortCardsInHand();						//Sorting them 
		}
		/*FOR APPENDIX (CARD ALLOCATION)
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<13; j++) {
				this.playerList.get((i+7)%4).addCard(deck.getCard(13*i+j));
			}
			this.playerList.get(i).sortCardsInHand();
		}*/
		
	
		D3= new BigTwoCard(0,2);											//Card with Suit=Diamond & Rank=3
		for (int i=0; i<4; i++) {
			if (this.getPlayerList().get(i).getCardsInHand().contains(D3)) {		//Checking which player has the Card D3
				currentIdx=i;
			}
		}
		table.repaint();
		table.printMsg("Player " + currentIdx +"'s turn: \n");
	}
	
	
	/** 
	 * This method is for making a move by a player with the specified playerID using the cards specified by the list of indices.
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		this.checkMove(playerID, cardIdx);
	}
	private int lastPlayer=-1;
	public int getLast() {return lastPlayer;}
	private boolean isNotaMove=false;
	
	/** The method for checking a move made by a player.
	 * 
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		
			if(handsOnTable.size()==0) {					//Checking the 1st Turn
				this.table.setActivePlayer(currentIdx);	//Setting Active Player
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
					table.printMsg("Player " + currentIdx +"'s turn: \n");

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
							table.printMsg("Player " + currentIdx +"'s turn: \n");

						}else {
							table.printMsg("Not a legal move!!!\n");		//Printing counter part.
							isNotaMove=true;
						}
					}
				}
			}
			if (endOfGame() == true) {
				
				table.printMsg("Game Ends\n");
				for(int i=0; i<4; i++) {
					if(this.getPlayerList().get(i).getNumOfCards()==0) {		//Checking which player has 0 Cards
						table.printMsg("Player "+i+" wins the game.\n");			//Printing the result accordingly 
					}
					else
						table.printMsg("Player "+ i + " has " + this.getPlayerList().get(i).getNumOfCards() + " cards in hand.\n");
				}
				table.disable();
			}
		
		
	}
	
	/**A method for starting the BigTwoGame.
	 * @param args not being used in this application.
	 */
	public static void main(String[] args) {
		BigTwo a = new BigTwo();				//Making a BigTwo object.
		a.getDeck().initialize();			//Initializing the BigTwoDeck
		a.getDeck().shuffle();				//Shuffling it
		a.start((BigTwoDeck) a.getDeck() );	//Passing it to the Start Function

	}
	/**The method for returning a valid hand from the specified list of cards of the player. 
	 * @param player The Player
	 * @param cards	Cards Supplied
	 * @return Hand(Depending which Hand) or Null 
	 */
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
	/** a method for checking if the game ends.
	 * @return True or False depending if Game Ended or not.
	 */
	public boolean endOfGame() {
		for (int i = 0; i < 4; i++)
			if (this.getPlayerList().get(i).getNumOfCards() == 0)
				return true;
		return false;
	}
	 void print(CardList cards,boolean printFront,boolean printIndex) {		//A function to print cards on the Text Area of the Frame  
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
	
	

}
