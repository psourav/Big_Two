
/**The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game. 
 * It overrides the initialize() function. 
 * @author souravpadhiari
 *
 */
public class BigTwoDeck extends Deck {
	
	
	private static final long serialVersionUID = 1L;

	/** This method initializes the deck of BigTwoCards.
	 * 
	 */
	public void initialize() {
		removeAllCards();		//First remove all the cards in the deck
		for (int i=0; i<13; i++)
		{
			for(int j=0; j<4; j++ ) {
				BigTwoCard card = new BigTwoCard(j,i);		//Creating new BigTwoCards
				addCard(card);								//Adding BigTwoCards to the Deck
				
			}
		}
	}
	/*FOR APPENDIX
	public void initialize() {
		removeAllCards();
		BigTwoCard card=new BigTwoCard(0,2);
		addCard(card);
		card=new BigTwoCard(1,3);
		addCard(card);
		card=new BigTwoCard(0,5);
		addCard(card);
		card=new BigTwoCard(0,6);
		addCard(card);
		card=new BigTwoCard(1,6);
		addCard(card);
		card=new BigTwoCard(1,7);
		addCard(card);
		card=new BigTwoCard(3,7);
		addCard(card);
		card=new BigTwoCard(1,8);
		addCard(card);
		card=new BigTwoCard(3,8);
		addCard(card);
		card=new BigTwoCard(2,11);
		addCard(card);
		card=new BigTwoCard(2,0);
		addCard(card);
		card=new BigTwoCard(1,1);
		addCard(card);
		card=new BigTwoCard(2,1);
		addCard(card);
		
		card=new BigTwoCard(2,3);
		addCard(card);
		card=new BigTwoCard(0,4);
		addCard(card);
		card=new BigTwoCard(1,4);
		addCard(card);
		card=new BigTwoCard(2,4);
		addCard(card);
		card=new BigTwoCard(3,4);
		addCard(card);
		card=new BigTwoCard(3,5);
		addCard(card);
		card=new BigTwoCard(0,7);
		addCard(card);
		card=new BigTwoCard(0,9);
		addCard(card);
		card=new BigTwoCard(1,10);
		addCard(card);
		card=new BigTwoCard(0,11);
		addCard(card);
		card=new BigTwoCard(3,11);
		addCard(card);
		card=new BigTwoCard(0,0);
		addCard(card);
		card=new BigTwoCard(0,1);
		addCard(card);
		
		card=new BigTwoCard(1,2);
		addCard(card);
		card=new BigTwoCard(0,3);
		addCard(card);
		card=new BigTwoCard(3,3);
		addCard(card);
		card=new BigTwoCard(3,6);
		addCard(card);
		card=new BigTwoCard(2,7);
		addCard(card);
		card=new BigTwoCard(0,8);
		addCard(card);
		card=new BigTwoCard(2,8);
		addCard(card);
		card=new BigTwoCard(1,9);
		addCard(card);
		card=new BigTwoCard(2,9);
		addCard(card);
		card=new BigTwoCard(0,10);
		addCard(card);
		card=new BigTwoCard(1,11);
		addCard(card);
		card=new BigTwoCard(1,12);
		addCard(card);
		card=new BigTwoCard(3,0);
		addCard(card);
		
		card=new BigTwoCard(2,2);
		addCard(card);
		card=new BigTwoCard(3,2);
		addCard(card);
		card=new BigTwoCard(1,5);
		addCard(card);
		card=new BigTwoCard(2,5);
		addCard(card);
		card=new BigTwoCard(2,6);
		addCard(card);
		card=new BigTwoCard(0,9);
		addCard(card);
		card=new BigTwoCard(2,10);
		addCard(card);
		card=new BigTwoCard(3,10);
		addCard(card);
		card=new BigTwoCard(0,12);
		addCard(card);
		card=new BigTwoCard(2,12);
		addCard(card);
		card=new BigTwoCard(3,12);
		addCard(card);
		card=new BigTwoCard(1,0);
		addCard(card);
		card=new BigTwoCard(3,1);
		addCard(card);
	}*/
		
	}
