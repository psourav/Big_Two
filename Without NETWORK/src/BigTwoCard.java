
/**The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game. 
 * It Overrides the compareTo function.
 * @author souravpadhiari
 *
 */
public class BigTwoCard extends Card {
	
	private static final long serialVersionUID = 1L;
	/**The constructor for building a card with the specified suit and rank. 
	 * 
	 * @param suit an int value between 0 and 3 representing the suit of a card:
	 *            <p>
	 *            0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank an int value between 0 and 12 representing the rank of a card:
	 *            <p>
	 *            0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11
	 *            = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit,rank);						// Calling the Supper Constructor
	}
	
	
	/** The method for comparing this card with the specified card for order.
	 * @param	card to which the BigTwoCard is being compared to.
	 * @return 	return 1 if greater than specified card.
	 * 			return 0 if equal specified card.
	 * 			return -1 if smaller than specified card.
	 */
	public int compareTo(Card card) {
		if(this.rank==0) {						//1.Checking if this BigTwoCard's Rank is A
			if(card.rank>=2 && card.rank<=12) {	//Comparing the BigTwoCard with specified Card accordingly
				return 1;
			}else if(card.rank==1) {
				return -1;
			}else {
				if (this.suit>card.suit) {
					return 1;
				}
				else if (this.suit==card.suit) {
					return 0;
				}
				else 
					return -1;
			}
				
		}else if(this.rank==1) {					//2.Checking if this BigTwoCard's Rank is 2
			if(card.rank>=2 && card.rank<=12) {	//Comparing the BigTwoCard with specified Card accordingly
				return 1;
			}else if(card.rank==0) {
				return 1;
			}else {
				if (this.suit>card.suit) {
					return 1;
				}
				else if (this.suit==card.suit) {
					return 0;
				}
				else 
					return -1;
			}
		}else if(card.rank==1) {						//Vice Versa of 2
			if(this.rank>=2 && this.rank<=12) {
				return -1;
			}else if(this.rank==0) {
				return -1;
			}else {
				if (card.suit>this.suit) {
					return -1;
				}
				else if (card.suit==this.suit) {
					return 0;
				}
				else 
					return 1;
			}
		}else if(card.rank==0) {					//Vice Versa of 1
			if(this.rank>=2 && this.rank<=12) {
				return -1;
			}else if(this.rank==1) {
				return 1;
			}else {
				if (card.suit>this.suit) {
					return -1;
				}
				else if (card.suit==this.suit) {
					return 0;
				}
				else 
					return 1;
			}
		}	
		else {								//Comparing the BigTwoCard with specified Card accordingly
			if (this.rank > card.rank) {
				return 1;
			} else if (this.rank < card.rank) {
				return -1;
			} else if (this.suit > card.suit) {
				return 1;
			} else if (this.suit < card.suit) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
}
