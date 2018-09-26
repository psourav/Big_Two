/**Flush Class is  the subclass the of Hand.
 * It overrides the beats(),getType() & isValid() function.
 * @author souravpadhiari
 *
 */
public class Flush extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */

	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/**Retrieves the type of Hand.
	 * @return Flush type. 
	 */
	public String getType() { 
		return "Flush";
	}
	/**The method for checking if this hand beats a specified hand. 
	 * @param hand is the specified hand.
	 * @return True/False if it beats the specified Hand or not.
	 */
	public boolean beats(Hand hand) {
		if (hand.getType()==this.getType()) {		
			if(this.getTopCard().suit>hand.getTopCard().suit) {//check if topcard suit is greater 
				return true;
			}
			else if(this.getTopCard().suit==hand.getTopCard().suit){//check if topcard suit is equal and check accordingly
				BigTwoCard X = (BigTwoCard) this.getTopCard();		
				if(X.compareTo(hand.getTopCard())==1) {
					return true;
				}
				else
					return false;
			}
			else 
				return false;
		}
		else if(hand.getType()=="Straight"){			//as Flush always beats Straight 
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
		boolean isV=true;
		if(this.size()==5) {		//if size is 5
			for(int i=0; i<this.size()-1; i++) {
				if(this.getCard(i).suit!=this.getCard(i+1).suit && isV==true)//check if all the suits are same
					isV=false;
			}
		}
		else 
			isV=false;
		
		return isV;
	}
}
