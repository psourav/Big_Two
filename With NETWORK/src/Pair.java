/**Pair Class is  the subclass the of Hand.
 * It overrides the getType() & isValid() function.
 * @author souravpadhiari
 *
 */

public class Pair extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/**Retrieves the type of Hand.
	 * @return Pair type. 
	 */
	public String getType() { 
		return "Pair";
	}
	/**Check if the Hand is Valid or not.
	 * 
	 * @return True or False based on the condition.
	 * 
	 */
	public boolean isValid() {
		if (this.size()==2)				//check the size
		{	if(getCard(0).rank==getCard(1).rank)			//check if both the ranks are same
			{
				return true;
			}
			
		}
		
		return false;
	}
	
}	
