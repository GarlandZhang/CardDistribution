/*
 * What is the problem?
 * -The best shuffling method that randomly distributes cards the most
 */

import org.apache.commons.math3.distribution.NormalDistribution; // import statement to get the package to use the CDF( cumulative distribution function ) 
/**
 * Started Feb. 21
 * @author zerot_000( Zhang, Garland )
 * Standard deck of cards here
 */
public class Deck
{
	public class Card { //inner class of Deck; Representation of cards

		private int val; //value of deck ***Jacks, queens, and kings, are represented by 11, 12, and 13 respectively.
		private String suit; //suit of the card ( ie: diamonds, clubs, hearts, spades )
		private Card topCard //card on top of the current card
					,bottomCard // card on bottom of the current card
					,startTopCard //the beginning card on top of the current card
					,startBottomCard; //the beginning card on bottom of the current card
		
		public Card( int val, String suit )
		{
			this.val = val;
			this.suit = suit;
		}
		
		/*
		 * getVal gets value of the card
		 * @return value
		 */
		public int getVal()
		{
			return val;
		}
		
		/*
		 * getSuit gets suit of the card
		 * @return suit
		 */
		public String getSuit()
		{
			return suit;
		}
		
		/*
		 * setLeft sets the neighbour card to the left( or top ) of the card
		 * @param the card on the left
		 */
		public void setLeft( Card left )
		{
			topCard = left;
		}
		
		/*
		 * setRight sets the neighbour card to the right( or bottom ) of the card
		 * @param the card on the right
		 */
		public void setRight( Card right )
		{
			bottomCard = right;
		}
	}
	
	
	private Card[][] deck; //columns are numbers, rows are suits
	private Card topCard, //top card of the deck
				 bottomCard; //bottom card of the deck
	private String[] suits = { "D", "C", "H", "S" }; //diamonds, clubs, hearts, spades
	private int[] values; 
	private double standardDeviation;
	private double mean; //average number of cards
	private int numOfMoves;
	public static final int DECK_SIZE = 52;
	
	public Deck()
	{
		deck = new Card[ 13 ][ 4 ];
		values = new int[ 13 ];
		standardDeviation = 0;
		mean = 0;
		numOfMoves = 0;
	}
	
	/*
	 * makeStandardDeck constructs a standard 52 card deck
	 * Imagine a 13 by 4 grid and filling each box with a card value and suit
	 * ie:
	  | 1D | 1C | 1H | 1S | 
	  | 2D | 2C | 2H | 2S | 
	  | 3D | 3C | 3H | 3S | 
	  | 4D | 4C | 4H | 4S | 
	  | 5D | 5C | 5H | 5S | 
	  | 6D | 6C | 6H | 6S | 
   	  | 7D | 7C | 7H | 7S | 
	  | 8D | 8C | 8H | 8S | 
	  | 9D | 9C | 9H | 9S | 
	| 10D | 10C | 10H | 10S | 
	| 11D | 11C | 11H | 11S | 
	| 12D | 12C | 12H | 12S | 
	| 13D | 13C | 13H | 13S |
	 */
	public void makeStandardDeck()
	{
		for( int i = 0; i < values.length; i++ )
		{
			for( int j = 0; j < suits.length; j++ )
			{
				deck[ i ][ j ] = new Card( i + 1, suits[ j ] );
			}
		}
		
		setTopCard( deck[ 0 ][ 0 ] );
		setBottomCard( deck[ deck.length - 1 ][ deck[ deck.length - 1 ].length - 1 ] );
	}

	/*
	 * setTopCard sets the top card in the deck
	 * @param top the top card of the deck
	 */
	public void setTopCard( Card top )
	{
		topCard = top;
	}
	
	/*
	 * getTopCard gets the top card
	 * @return top card of deck
	 */
	public Card getTopCard()
	{
		return topCard;
	}
	
	/*
	 * setTopCard sets the bottom card in the deck
	 * @param bottom the bottom card of the deck
	 */
	public void setBottomCard( Card bottom )
	{
		bottomCard = bottom;
	}
	
	/*
	 * getBottomCard gets the bottom card
	 * @return bottom card of deck
	 */
	public Card getBottomCard()
	{
		return bottomCard;
	}
	
	/*
	 * setDeviation sets the standard deviation range
	 * @param val the number of cards taken
	 * In an egg shell, deviation is just how distributed the cards are
	 * Please look at http://www.biologyforlife.com/uploads/2/2/3/9/22392738/8058314_orig.jpg 
	 */
	public void setDeviation( int val )
	{
		standardDeviation = val;
	}
	
	/*
	 * getDeviation gets the value of standardDeviation
	 */
	public double getDeviation()
	{
		return standardDeviation;
	}
	
	/*
	 * setMean finds and sets the mean
	 * @param mean the mean value
	 */
	public void setMean( int mean )
	{
		this.mean = mean;
	}
	
	/*
	 * getMean gets the mean
	 * @return the mean
	 */
	public double getMean()
	{
		return mean;
	}
	
	/*
	 * getNumOfMoves get the number of moves
	 * @return number of moves
	 */
	public int getNumOfMoves()
	{
		return numOfMoves;
	}
	
	/*
	 * createList creates a linked list where all cards point to the next card in the deck, with the exception of the last card
	 * **IMPORTANT. Like setting the top card and bottom card to a given value, it is important to be able to reference to a card when necessary
	 * Essentially, here is where the cards are set to their cards on their 'left' and 'right' or 'top' and 'bottom'
	 */
	
	public void createList()
	{
		for( int i = 0; i < deck.length; i++ )
		{
			for( int j = 0; j < deck[ i ].length; j++ )
			{
				
				Card current = deck[ i ][ j ];
				
				if( i == 0 && j == 0 )
				{
					topCard.setRight( deck[ i ][ j + 1 ] );
					topCard.startTopCard = getBottomCard();
					topCard.startBottomCard = topCard.bottomCard;
				}
				else if( i == deck.length - 1 && j == deck[ i ].length - 1 )
				{
					bottomCard.setLeft( deck[ i ][ j - 1 ] );
					bottomCard.startBottomCard = getTopCard();
					bottomCard.startTopCard = bottomCard.topCard;
				}
				else
				{
					Card previous;
					Card after;
					
					if( j == 0 )
					{
						previous = deck[ i - 1 ][ deck[ i ].length - 1 ];
						after = deck[ i ][ j + 1 ];
					}
					else if( j == 3 )
					{
						previous = deck[ i ][ j - 1 ];
						after = deck[ i + 1 ][ 0 ];
					}
					else
					{
						previous = deck[ i ][ j - 1 ];
						after = deck[ i ][ j + 1 ];
					}
					current.setLeft( previous );//SOMEWHERE HERE
					current.startTopCard = previous;
					current.setRight( after );
					current.startBottomCard = after;
				}
			}
		}
	}
	
	/*
	 * lengthOfDeck the length of the given 'deck'
	 * @param bottomCard the bottom card of a 'deck'
	 * @return the length of the 'deck'
	 */
	public int lengthOfDeck( Card bottomCard )
	{
		return bottomCard != null ? 1 + lengthOfDeck( bottomCard.topCard ) : 0; //recursion
	}
	
	/*
	 * generate creates a random number
	 * @return returns a random number
	 */
	public int generate()
	{
		NormalDistribution nd = new NormalDistribution( mean, standardDeviation );
		double[] randomNumbers = new double[ DECK_SIZE ];
		double sum = 0;
		
		for( int i = 1; i <= DECK_SIZE; i++ )
		{
			double num = Math.abs( nd.cumulativeProbability( i ) - nd.cumulativeProbability( i - 1 ) ) * 1000000;
			sum = sum + num;
			randomNumbers[ i - 1 ] = sum;
		}
		
		double val = Math.random() * 1000000 + 1;
		for( int i = 0; i < randomNumbers.length - 1; i++ )
		{
			double num = randomNumbers[ i ];
			if( val < num )
			{
				return i + 1;
			}
		}
		return randomNumbers.length;
	}
	
	/*
	 * generatePosition creates a random number to pick position of where to take cards from
	 * @return returns a random number
	 */
	public int generatePosition()
	{
		NormalDistribution nd = new NormalDistribution( ( DECK_SIZE + 1 ) / 2, standardDeviation );
		double[] randomNumbers = new double[ DECK_SIZE - 2 ];//excludes top and bottom
		double sum = 0;
		for( int i = 1; i <= DECK_SIZE - 2; i++ )
		{
			double num = Math.abs( nd.cumulativeProbability( i ) - nd.cumulativeProbability( i - 1 ) ) * 1000000;
			sum = sum + num;
			randomNumbers[ i - 1 ] = sum;
		}
		
		double val = Math.random() * 1000000;
		for( int i = 0; i < randomNumbers.length; i++ )
		{
			double num = randomNumbers[ i ];
			if( val < num )
			{
				return i;
			}
		}
		return randomNumbers.length;
	}
	
	/*
	 * search searches for card in deck
	 * @param position of the card
	 * @param current current card in the deck
	 * @return returns card in that position
	 */
	public Card search( int position, Card current )
	{
			return position == 0? current : search( position - 1, current.topCard );
	}
	
	/*
	 * searchFromTop starts from the top and moves down the deck to find card
	 * @param position of the card
	 * @param current current card in the deck
	 * @return returns card in that position
	 */
	public Card searchFromTop( int position, Card current )
	{
			return position == 0? current : searchFromTop( position - 1, current.bottomCard );
	}
	
	/*
	 * returnDeckFromTop returns the deck from the top
	 * @param point the card to start off with
	 */
	public void returnDeckFromTop( Card point )
	{
		if( point != null )
		{
			System.out.print( point.getVal() + point.getSuit() + " " );
			returnDeckFromTop( point.bottomCard );
		}
	}
	
	/*
	 * returnDeckFromBottom returns the deck from the bottom
	 * @param point the card to start off with
	 */
	public void returnDeckFromBottom( Card point )
	{
		if( point != null )
		{
			System.out.print( point.getVal() + point.getSuit() + " " );
			returnDeckFromBottom( point.topCard );
		}
	}

	/*
	 * getAverageDistance gets the average distance between cards
	 * @return average distance
	 * Two pointers are used where one is pointing to cards preceding the card( card above ) and one is pointing to cards succeeding the card( card below ) in order to find the two original starting neighbors of the current card
	 */
	public double getDistance()
	{
		Card point = getBottomCard();
		double total = 0;
		while( point != null )
		{
			int sum = 0;
			boolean foundTop = false;
			boolean foundBottom = false;
			
			Card left = null;
			Card right = null;
		
			if( point == getBottomCard() )
			{
				left = point.topCard;
				right = getTopCard();
			}
			else if( point == getTopCard() )
			{
				left = getBottomCard();
				right = point.bottomCard;
			}
			else
			{
				right = point.bottomCard;
				left = point.topCard;
			}
			
			for( int i = 1, j = 1; !foundTop || !foundBottom; )
			{
				
				if( !foundTop )
				{
					if( left == point.startTopCard )
					{
						sum = sum + ( i > 26? DECK_SIZE - i : i );
						foundTop = true;
					}
					else
					{
						i++;
						if( left.topCard == null )
						{
							left = getBottomCard();
						}
						else
						{
							left = left.topCard;
						}
					}
				}
				
				if( !foundBottom )
				{
					if( right == point.startBottomCard )
					{
						sum = sum + ( j > 26? DECK_SIZE - j : j );
						foundBottom = true;
					}
					else
					{
						j++;
						if( right.bottomCard == null )
						{
							right = getTopCard();
						}
						else
						{
							right = right.bottomCard;
						}
					}
				}
			}
			
			double average = sum / 2.0;
			total = total + average;
			point = point.topCard;
		}
		return total;
	}
	
	/*
	 * overhandshuffle "shuffles" the deck just once
	 * Explanation: Take a chunk of cards from some position in the middle of the deck and place it on top of the remaining deck
	 */
	public void overhandshuffle()
	{
		int position = generatePosition();
		int num = generate();
		
		if( position + num >= 52 )
		{
			num = 51 - position;
		}
		
		Card bottom = search( num, getBottomCard() );
		Card point = search( position, bottom );
		Card top = point;
			if( position != 0 )
			{
				Card topRight = top.bottomCard;
				Card bottomRight = bottom.bottomCard;
				top.bottomCard = bottomRight;
				bottomRight.topCard = top;
				topRight.topCard = null;
				bottom.bottomCard = getTopCard();
				topCard.topCard = bottom;
				setTopCard( topRight );
				
			}
			else
			{
				Card temp = top.bottomCard;
				topCard.setLeft( getBottomCard() );
				bottomCard.setRight( getTopCard() );
				temp.setLeft( null );
				top.setRight( null );
				setTopCard( temp );
				setBottomCard( top );
				
			}
			numOfMoves++;
		}
	
	/*
	 * riffleshuffle shuffles the deck using the riffle shuffle technique
	 * Explanation: Cut the deck in half, then alternate cards( from the bottom ) between each deck, creating a new deck
	 */
	public void riffleshuffle()
	{
		int position = generatePosition(); //gets position of where to divide deck in 'half'
		
		if( position <= 52 && position > 0 )
		{
	
		Card point = search( position - 1, getBottomCard() );
		
		Card topFirstHalf = getTopCard(); //top card of the first half of the original deck
		Card topSecondHalf = point; //top card of the second half of the original deck
		
		point = point.topCard;
		point.bottomCard = null;
		
		Card bottomFirstHalf = point;
		Card bottomSecondHalf = getBottomCard();
		
		topSecondHalf.topCard = null;
		
		Card topOfNewDeck = null;
		Card bottomOfNewDeck = null;
		
		boolean finishedShuffling = false;
		
		while( !finishedShuffling )
		{
			
			boolean chooseFirstDeck = Math.random() * 2 < 1;
			
			if( bottomFirstHalf == null )
			{
				bottomSecondHalf.bottomCard = topOfNewDeck;
				topOfNewDeck.topCard = bottomSecondHalf;
				topOfNewDeck = topSecondHalf;
				finishedShuffling = true;
			}
			else if( bottomSecondHalf == null )
			{
				bottomFirstHalf.bottomCard = topOfNewDeck;
				topOfNewDeck.topCard = bottomFirstHalf;
				topOfNewDeck = topFirstHalf;
				finishedShuffling = true;
			}
			
			else if( chooseFirstDeck )
			{
				int lengthOfDeck = lengthOfDeck( bottomFirstHalf );
				int numOfCards = generate();
				if( numOfCards > lengthOfDeck )
				{
					numOfCards = lengthOfDeck;
				}
				point = search( numOfCards - 1, bottomFirstHalf );
				if( bottomOfNewDeck != null )
				{
					bottomFirstHalf.bottomCard = topOfNewDeck;
					topOfNewDeck.topCard = bottomFirstHalf;
				}
				else
				{
					bottomOfNewDeck = bottomFirstHalf;
				}
				topOfNewDeck = point;
				
				if( point != topFirstHalf )
				{
					point = point.topCard;
					point.bottomCard = null;
					topOfNewDeck.topCard = null;
					bottomFirstHalf = point;
				}
				else
				{
					bottomFirstHalf = null;
				}
				
			}
			else
			{
				int lengthOfDeck = lengthOfDeck( bottomSecondHalf );
				int numOfCards = generate();
				if( numOfCards > lengthOfDeck )
				{
					numOfCards = lengthOfDeck;
				}
				point = search( numOfCards - 1, bottomSecondHalf );
				
				if( bottomOfNewDeck != null )
				{
					bottomSecondHalf.bottomCard = topOfNewDeck;
					topOfNewDeck.topCard = bottomSecondHalf;
				}
				else
				{
					bottomOfNewDeck = bottomSecondHalf;
				}
				topOfNewDeck = point;
			
				if( point != topSecondHalf )
				{
					point = point.topCard;
					point.bottomCard = null;
					topOfNewDeck.topCard = null;
					bottomSecondHalf = point;	
				}
				else
				{
					bottomSecondHalf = null;
				}
				
			}
			numOfMoves++;
		}
			setTopCard( topOfNewDeck );
			setBottomCard( bottomOfNewDeck );
		}
	}
	
	/*
	 * hindushuffle shuffles the deck using the hindu shuffle technique
	 * Explanation: Take cards from bottom of deck then from top of extracted deck and peel off cards until all cards are in one deck
	 */
	public void hindushuffle()
	{
		int numOfCards = generate();
		if( numOfCards == 52 )
		{
			numOfCards--;
		}
		Card point = search( numOfCards, getBottomCard() );
		Card top = point.bottomCard; // top of extracted deck
		Card bottom = getBottomCard();// bottom of extracted deck
		Card origBottom = point;
		top.topCard = null;
		origBottom.bottomCard = null;
		boolean finished = false;
		
		while( !finished )
		{
			int lengthOfDeck = lengthOfDeck( bottom );
			numOfCards = generate();
			if( numOfCards > lengthOfDeck )
			{
				numOfCards = lengthOfDeck;
			}
			point = searchFromTop( numOfCards - 1, top ); // subtract 1 so number of cards picked up do not exceed length of deck ( and since 0 is considered 1, 1 is 2 ... need to -1 )
			if( point == bottom )
			{
				point.bottomCard = getTopCard();
				getTopCard().topCard = point;
				setTopCard( top );
				finished = true;
			}
			else
			{
				Card tempBottom = point;
				point = point.bottomCard;
				point.topCard = null;
				getTopCard().topCard = tempBottom;
				tempBottom.bottomCard = getTopCard();
				setTopCard( top );
				top = point;
			}
			numOfMoves++;
		}
		
		setBottomCard( origBottom );
		
	}
	
	/*
	 * pileOn stacking segments of the deck on top of one another
	 * Divide into piles and stack cards together.
	 */
	public void pileOn()
	{
		Card bottomOfDeck = getBottomCard();
		
		Card[] subdecksTops = new Card[ DECK_SIZE ];
		Card[] subdecksBottoms = new Card[ DECK_SIZE ];
		int i = 0;
		
		while( bottomOfDeck != null )
		{
			int lengthOfDeck = lengthOfDeck( bottomOfDeck );
			int numOfCards = generate();
			if( numOfCards >= lengthOfDeck )
			{
				numOfCards = lengthOfDeck;
			}
			Card point = search( numOfCards - 1, bottomOfDeck );
			Card top = point;
			subdecksTops[ i ] = top;
			subdecksBottoms[ i ] = bottomOfDeck;
			if( point == getTopCard() )
			{
				bottomOfDeck = null;
			}
			else
			{
				point = point.topCard;
				bottomOfDeck = point; //will point to null intentionally by setting point to null( point = point.topCard where point is top card will be null )
				point.bottomCard = null;
				top.topCard = null;
			}
			i++;
			numOfMoves++;
		}
		
		Card bottomOfNewDeck = null;
		Card topOfNewDeck = null;
		int j = i;
		
		while( j != 0 )
		{	
			int deckNum = (int) ( Math.random() * i );
			if( subdecksTops[ deckNum ] != null )
			{
				if( bottomOfNewDeck == null )
				{
					topOfNewDeck = subdecksTops[ deckNum ];
					bottomOfNewDeck = subdecksBottoms[ deckNum ];
					subdecksTops[ deckNum ] = null;
				}
				else
				{
					subdecksBottoms[ deckNum ].bottomCard = topOfNewDeck;
					topOfNewDeck.topCard = subdecksBottoms[ deckNum ];
					topOfNewDeck = subdecksTops[ deckNum ];
					subdecksTops[ deckNum ] = null;
				}
				j--;
				numOfMoves++;
			}
		}
		setTopCard( topOfNewDeck );
		setBottomCard( bottomOfNewDeck );
	}
}
