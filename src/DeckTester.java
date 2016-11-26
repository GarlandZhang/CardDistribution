
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

/**
 * 
 * @author zerot_000
 * Tests deck
 * *shuffling techniques, hindu, riffle, overhand, piles
 */
public class DeckTester extends JFrame {

	private TextField numOfShufflesField,
					  deviationField,
					  meanField,
					  messageField;
	
	private JButton OHSButton,
					RSButton,
					HSButton,
					PSButton,
					enterButton,
					clearButton;
	
	private String shuffleType;
	
	Deck TestDeck;
	
	private static final int DECK_SIZE = 52;
					  
	public DeckTester()
	{
		
		shuffleType = "";
		
		setTitle( "User input" );
		setLayout( new BorderLayout() );
		
		JPanel control = new JPanel( new FlowLayout() );
		
		//shuffle type field
		
		JPanel shuffleTypes = new JPanel( new GridLayout() );
		
		OHSButton = new JButton( "Overhand shuffle" );
		OHSButton.addActionListener( new OverhandPressed() );
		
		RSButton = new JButton( "Riffle shuffle" );
		RSButton.addActionListener( new RifflePressed() );
		
		HSButton = new JButton( "Hindu shuffle" );
		HSButton.addActionListener( new HinduPressed() );
		
		PSButton = new JButton( "Piles" );
		PSButton.addActionListener( new PilePressed() );
		
		shuffleTypes.add( OHSButton );
		shuffleTypes.add( RSButton );
		shuffleTypes.add( HSButton );
		shuffleTypes.add( PSButton );
		
		JLabel shuffleTypeLabel = new JLabel( "Shuffle Type: " );
		
		control.add( shuffleTypeLabel );
		control.add( shuffleTypes );
		
		add( control, BorderLayout.NORTH );
		
		JPanel control2 = new JPanel( new FlowLayout() );
		
		//number of shuffles text field
		numOfShufflesField = new TextField();
		
		JLabel numOfShufflesLabel = new JLabel( "# of shuffles: " );
		
		control2.add( numOfShufflesLabel );
		control2.add( numOfShufflesField );
		
		//size of one deviation text field
		deviationField = new TextField();
		
		JLabel deviationLabel = new JLabel( "Deviation size: " );
		
		control2.add( deviationLabel );
		control2.add( deviationField );
		
		//mean text field
		meanField = new TextField();
		
		JLabel meanLabel = new JLabel( "Mean( average ) # of cards: " );
		
		control2.add( meanLabel );
		control2.add( meanField );
		
		//enter button
		enterButton = new JButton( "Enter" );
		enterButton.addActionListener( new EnterButtonPressed() );
		
		control2.add( enterButton );
		
		//clear button
		clearButton = new JButton( "Clear" );
		clearButton.addActionListener( new ClearButtonPressed() );
		clearButton.setEnabled( false );
		
		control2.add( clearButton );
		
		//add control2
		add( control2, BorderLayout.CENTER );
		
		//message field
		messageField = new TextField();
		messageField.setEditable( false );
		
		add( messageField, BorderLayout.SOUTH );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		
	}
	
	class EnterButtonPressed implements ActionListener
	{
		//if enter button is pressed, perform this action
		public void actionPerformed( ActionEvent AE )
		{
			try
			{
				//initialize TestDeck
				TestDeck = new Deck();
				TestDeck.makeStandardDeck();
				TestDeck.createList();
		
				final int DEVIATION_SIZE = Integer.parseInt( deviationField.getText() );
				TestDeck.setDeviation( DEVIATION_SIZE );
				
				final int MEAN = Integer.parseInt( meanField.getText() );
				TestDeck.setMean( MEAN );
				
				enterButton.setEnabled( false );
				numOfShufflesField.setEnabled( false );
				deviationField.setEnabled( false );
				meanField.setEnabled( false );
				messageField.setEditable( false );
				clearButton.setEnabled( true );
				
				for( int i = 0; i < Integer.parseInt( numOfShufflesField.getText() ); i++ )
				{
					if( shuffleType.equals( "Overhand" ) )
					{
						TestDeck.overhandshuffle();
					}
					else if( shuffleType.equals( "Riffle" ) )
					{
						TestDeck.riffleshuffle();
					}
					else if( shuffleType.equals( "Hindu" ) )
					{
						TestDeck.hindushuffle();
					}
					else if( shuffleType.equals( "Pile" ) )
					{
						TestDeck.pileOn();
					}
					else
					{
						messageField.setText( "No shuffle type chosen." );
						break;
					}
				}
				
				if( !shuffleType.equals( "" ) )
				{
					messageField.setText( "Average distance: " + TestDeck.getDistance() / DECK_SIZE + " | Number of iterations: " + TestDeck.getNumOfMoves() );
				}
			}
			catch( Exception e )
			{
				messageField.setText( "Invalid input." );
			}
		}
	}
	
	class ClearButtonPressed implements ActionListener
	{
		public void actionPerformed( ActionEvent AE )
		{
			//resets all fields
			clearButton.setEnabled( false );
			enterButton.setEnabled( true );
			shuffleType.equals( "" );
			
			numOfShufflesField.setEnabled( true );
			numOfShufflesField.setText( "" );
			
			deviationField.setEnabled( true );
			deviationField.setText( "" );
			
			meanField.setEnabled( true );
			meanField.setText( "" );
			
			messageField.setText( "" );
			
			OHSButton.setEnabled( true );
			RSButton.setEnabled( true );
			HSButton.setEnabled( true );
			PSButton.setEnabled( true );
		}
	}
	
	class OverhandPressed implements ActionListener
	{
		public void actionPerformed( ActionEvent AE )
		{
			shuffleType = "Overhand";
			OHSButton.setEnabled( false );
			RSButton.setEnabled( false );
			HSButton.setEnabled( false );
			PSButton.setEnabled( false );
		}
	}
	
	class RifflePressed implements ActionListener
	{
		public void actionPerformed( ActionEvent AE )
		{
			shuffleType = "Riffle";
			OHSButton.setEnabled( false );
			RSButton.setEnabled( false );
			HSButton.setEnabled( false );
			PSButton.setEnabled( false );
		}
	}
	
	class HinduPressed implements ActionListener
	{
		public void actionPerformed( ActionEvent AE )
		{
			shuffleType = "Hindu";
			OHSButton.setEnabled( false );
			RSButton.setEnabled( false );
			HSButton.setEnabled( false );
			PSButton.setEnabled( false );
		}
	}
	
	class PilePressed implements ActionListener
	{
		public void actionPerformed( ActionEvent AE )
		{
			shuffleType = "Pile";
			OHSButton.setEnabled( false );
			RSButton.setEnabled( false );
			HSButton.setEnabled( false );
			PSButton.setEnabled( false );
		}
	}
	
	public static void main( String[]args ) throws FileNotFoundException
	{
		DeckTester DT = new DeckTester();
		DT.pack();
		DT.setVisible( true );
		
		/*
		boolean done = false; //checks if program is done
		while( !done )
		{
			
			Scanner userRead = new Scanner( System.in );
			PrintStream ps = new PrintStream( "SFOutput.txt" );
			PrintStream ps2 = new PrintStream( "Mean.txt" );
			PrintStream ps3 = new PrintStream( "Deviation.txt" );
			
			System.out.println( "Choose your shuffle method: \n 1. Overhand Shuffle \t 2. Riffle Shuffle \n 3. Hindu Shuffle \t 4. Piles" ); //prompts user to choose type of shuffle method
			int userVal = userRead.nextInt();

			final int START_SHUFFLE_SIZE = 1;
			
			System.out.print( "Set number of shuffles: " );
			final int NUM_OF_SHUFFLES = userRead.nextInt(); //Set number of shuffles
			
			final int START_DEVIATION_SIZE = 1;
			
			final int NUM_OF_DEVIATIONS = 40; //number of deviations affect outcome of cards
			
			final int START_MEAN = 1;
			
			final int END_MEAN = 52;
			
			final int DECK_SIZE = 52;//size of deck
			
			double averageConclusive = 0; //the conclusion of on average distance of cards from starting position
			int numOfTimesTotal = 0; //number of times the shuffle has occurred. **PROMPT yourself with this question: is the loops I have used an accurate depiction to model realistic shuffles( for example do I need nested loops if in reality only one loop is necessary? ).
	
			for( int i = START_MEAN; i <= END_MEAN; i++ )
			{
				double total = 0;
				for( int j = START_DEVIATION_SIZE; j <= NUM_OF_DEVIATIONS; j++ )
				{
					Deck deck1 = new Deck();
					deck1.makeStandardDeck();
					deck1.createList();
					deck1.setDeviation( j );
					deck1.setMean( i );
					
					for( int k = START_SHUFFLE_SIZE; k <= NUM_OF_SHUFFLES; k++ )
					{
						if( userVal == 1 )
						{
							//overhand shuffle
							deck1.overhandshuffle();
						}
						else if( userVal == 2 )
						{
							//riffle shuffle
							deck1.riffleshuffle();
						}
						else if( userVal == 3 )
						{
							//hindu shuffle
							deck1.hindushuffle();
						}
						else if( userVal == 4 )
						{
							//piles
							deck1.pileOn();
						}
					}
	
					double distance = deck1.getDistance();
					total = total + distance;
					numOfTimesTotal = numOfTimesTotal + deck1.getNumOfMoves();
					ps.println( ( distance / DECK_SIZE ) );	
					ps.flush();
					ps2.println( i );
					ps2.flush();
					ps3.println(j );
					ps3.flush();
				}
				System.out.println( total / ( NUM_OF_DEVIATIONS * DECK_SIZE ) ); 
				averageConclusive = averageConclusive + total;
			}
			ps.close();
			ps2.close();
			ps3.close();
			averageConclusive = averageConclusive / ( END_MEAN * NUM_OF_DEVIATIONS * DECK_SIZE ); //END_OF_RANGE-to get number of cards tested. . . NUM_OF_DEVIATIONS - START_DEVIATION_SIZE- to get number of deviations . . . DECK_SIZE-average not calculated for each individual card's distance so it is done here
			System.out.print( "============================================================== \n" );
			System.out.println( "Average distance: " + averageConclusive + " | Number of moves: " + numOfTimesTotal + "\n" );
			
			System.out.print( "Exit? Type yes. Otherwise enter any other key." );
			String userExit = userRead.next();
			
			done = userExit.equals( "Y" ) || userExit.equals( "y" ) || userExit.equals( "yes" ) || userExit.equals( "Yes" ) || userExit.equals( "YES" ); //checks if user wants to terminate program
			System.out.print( "\n============================================================== \n\n" );
		}
		
		System.out.println( "Good Bye." );
		*/
	}
}
