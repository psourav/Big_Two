
/**The Hand class is a subclass of the CardList class, and is used to model a hand of cards. 
 * It has a private instance variable for storing the player who plays this hand. 
 * It also has methods for getting the player of this hand, checking if it is a valid hand, getting the type of this hand, getting the top card of this hand, and checking if it beats a specified hand.
 * @author souravpadhiari
 *
 */
public class Hand extends CardList {
	
	private static final long serialVersionUID = 1L;
	private CardGamePlayer player;
	public Hand() {
		
	}
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand. 
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player=player;					//setting the player value
		
		for(int i=0; i<cards.size(); i++)
		{
			this.addCard(cards.getCard(i));	//adding Cards to Hand
		}
		
	}
	/**The method for retrieving the player of this hand. 
	 * @return the player.
	 */
	public CardGamePlayer getPlayer(){
		return player;
	}
	/**The method for retrieving the top card of this hand. 
	 * @return the topcard in the cardlist.
	 */
	public Card getTopCard() {
		Card X = this.getCard(0);			//Setting the 1st Card as X
		for (int i=1; i< this.size(); i++ ) {
			Card A =  this.getCard(i);		//Setting the ith Card as X
			if(X.compareTo(A)==1) {			//Checking which one is greater
				X=A;							//Making X as A
			}
		}
		return X ;							
	}
	
	/**The method for checking if this hand beats a specified hand. 
	 * @param hand is the specified hand.
	 * @return True/False if it beats the specified Hand or not.
	 */
	public boolean beats(Hand hand) {
		Card T = this.getTopCard();				//Setting the topCard as T
		if (hand.getType()==this.getType()) {	//Checking the type of Hand
			if(T.compareTo(hand.getTopCard())==1) {	//Check if the TopCard is bigger than  the other specified hand topcard
				return true;
			}									
			else 
				return false;
		}
		else
			return false;
	}
	
	/**Check if the Hand is Valid or not.
	 * Abstract in this class.
	 * @return not used here
	 * 
	 */
	public boolean isValid() {				
		return false;
	}
	/**Retrieves the type of Hand.
	 * Abstract in this Class
	 * @return null as this class doesn't have a type. 
	 */
	public String getType() {				
		return null;
	}
}
