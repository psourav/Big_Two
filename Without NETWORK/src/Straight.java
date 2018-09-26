/**Straight Class is  the subclass the of Hand.
 * It overrides the getType() & isValid() function.
 * @author souravpadhiari
 *
 */
public class Straight extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**Retrieves the type of Hand.
	 * @return Straight type. 
	 */
	public String getType() { 
		return "Straight";
	}
	/**Check if the Hand is Valid or not.
	 * 
	 * @return True or False based on the condition.
	 * 
	 */
	public boolean isValid() {
		boolean isV=false;
		this.sort();				//sort the list
		if(this.size()==5) {	//if size is 5
			if((getCard(0).rank==10)&&(getCard(1).rank==11)&&(getCard(2).rank==12)&&(getCard(3).rank==0)&&(getCard(4).rank==1)) {// check the condition that 2 and A can only make straight with K but not with 3
				isV=true;
			}else if((getCard(1).rank-getCard(0).rank==1) && (getCard(2).rank-getCard(1).rank==1) && (getCard(3).rank-getCard(2).rank==1) && (getCard(4).rank-getCard(3).rank==1) ) {//check if the difference between consecutive cards is 1
				isV=true;
			}
		}
		
		
		
		return isV;

	}
		
	
	
	
}
