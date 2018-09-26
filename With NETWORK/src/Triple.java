/**Triple Class is  the subclass the of Hand.
 * It overrides the getType() & isValid() function.
 * @author souravpadhiari
 *
 */
public class Triple extends Hand {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a hand with the specified player and list of cards. 
	 * @param player of whom the CardList is.
	 * @param cards the CardList of cards to make Hand.
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
	/**Retrieves the type of Hand.
	 * @return Triple type. 
	 */
	public String getType() { 
		return "Triple";
	}
	/**Check if the Hand is Valid or not.
	 * 
	 * @return True or False based on the condition.
	 * 
	 */
	public boolean isValid() {
		if(this.size()==3)			//check size
		{	if(getCard(0).rank==getCard(1).rank && getCard(2).rank==getCard(1).rank )		//check if all 3 cards have same rank
			{
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}

	
}
