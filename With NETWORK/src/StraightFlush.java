/**StraightFlush Class is  the subclass the of Hand.
 * It overrides the beats(),getType() & isValid() function.
 * @author souravpadhiari
 *
 */
public class StraightFlush extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/**Retrieves the type of Hand.
	 * @return StraightFlush type. 
	 */
	public String getType() { 
		return "StraightFlush";
	}
	
	/**The method for checking if this hand beats a specified hand. 
	 * @param hand is the specified hand.
	 * @return True/False if it beats the specified Hand or not.
	 */
	public boolean beats(Hand hand) {
		if (hand.getType()==this.getType()) {
			BigTwoCard X= (BigTwoCard) this.getTopCard();
			if(X.compareTo(hand.getTopCard())==1) {		//Compare the topcards
				return true;
			}
			else 
				return true;
		}
		else if(hand.getType()=="Straight"||hand.getType()=="Flush"||hand.getType()=="FullHouse"||hand.getType()=="Quad"){//StraightFlush always beats Straight, Flush, FullHouse, Quad
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
		boolean FLisV=true;														//Check if its a flush
		if(this.size()==5) {				
			for(int i=0; i<this.size()-1; i++) {
				if(this.getCard(i).suit!=this.getCard(i+1).suit && FLisV==true)
					FLisV=false;
			}
		}
		else 
			FLisV=false;
		
		boolean isV=false;
		if (this.size()==5){
			if(FLisV) { //if its a FLush check if its a straight 
				if((getCard(0).rank==10)&&(getCard(1).rank==11)&&(getCard(2).rank==12)&&(getCard(3).rank==0)&&(getCard(4).rank==1)) {
					isV=true;
				}else if((getCard(1).rank-getCard(0).rank==1) && (getCard(2).rank-getCard(1).rank==1) && (getCard(3).rank-getCard(2).rank==1) && (getCard(4).rank-getCard(3).rank==1) ) {
					isV=true;
				}
			}
				
		}
		
			
		return isV;
		
		
		
	}
	
	

}
