/**Quad Class is  the subclass the of Hand.
 * It overrides the getTopCard(), beats(), getType() & isValid() function.
 * @author souravpadhiari
 *
 */
public class Quad extends Hand {

	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/**Retrieves the type of Hand.
	 * @return Quad type. 
	 */
	public String getType() { 
		return "Quad";
	}
	/**The method for retrieving the top card of this hand. 
	 * @return the topcard in the cardlist.
	 */
	public Card getTopCard() {
		this.sort();		//sort the cards
		Card T = this.getCard(0);		//Choose a card
		for(int i=0; i<this.size()-3; i++)
		{
			if (this.getCard(i).rank == this.getCard(i+1).rank && this.getCard(i+1).rank == this.getCard(i+2).rank && this.getCard(i+2).rank==this.getCard(i+3).rank) {
				T =getCard(i+3);		//replace T if the value of this card is greater
			}
		}
		return T;
	}
	/**The method for checking if this hand beats a specified hand. 
	 * @param hand is the specified hand.
	 * @return True/False if it beats the specified Hand or not.
	 */
	public boolean beats(Hand hand) {
		if (hand.getType()==this.getType()) {
			if(this.getTopCard().suit>hand.getTopCard().suit) {	//compare topcard
				return true;
			}
			else 
				return false;
		}
		else if(hand.getType()=="Straight"||hand.getType()=="Flush"||hand.getType()=="FullHouse"){	//Quad always beats Straight||Flush||FullHouse
			return true;
			
		}
		else
			return false;
	}
	/**Check if the Hand is Valid or not.
	 * 
	 * @return True or False based on the condition.
	 * 
	 */
	public boolean isValid() {
		boolean isV = false;
		this.sort();			//sort the cards
		if(this.size()==5)		//check if size is 5
		{
			if(this.getCard(0).rank==this.getCard(1).rank && this.getCard(1).rank==this.getCard(2).rank && this.getCard(2).rank==this.getCard(3).rank) {//check if 1st 4 cards are same
				isV = true;
			}
			else if(this.getCard(1).rank==this.getCard(2).rank && this.getCard(2).rank==this.getCard(3).rank && this.getCard(3).rank==this.getCard(4).rank) {//check if last 4 cards are same
				isV=true;
			}
			else 
				isV=false;
			
		}else 
			isV=false;
		return isV;
	}

}
