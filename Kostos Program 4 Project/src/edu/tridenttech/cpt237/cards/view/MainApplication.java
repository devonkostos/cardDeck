/*
 * Simulate a playing 
 * card shuffler using
 * multiple classes.
 * 
 */

package edu.tridenttech.cpt237.cards.view;

import edu.tridenttech.cpt237.cards.model.Deck;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		CardDisplayWindow ui = new CardDisplayWindow(primaryStage, 13);
		Deck deck = new Deck();
		deck.shuffle();
		ui.show(deck);
	}

	public static void main(String[] args)
	{
		Application.launch(args);
	}
}//END class MainApplication
