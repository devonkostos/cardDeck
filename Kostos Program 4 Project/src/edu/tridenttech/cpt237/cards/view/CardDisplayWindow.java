/*
 * Class to build 
 * and display UI, 
 * as well as react 
 * to user interaction.
 * 
 */

package edu.tridenttech.cpt237.cards.view;

import java.util.Collections;

import edu.tridenttech.cpt237.cards.model.Card;
import edu.tridenttech.cpt237.cards.model.Deck;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardDisplayWindow
{
	private final int SUIT_SIZE = 13;
	private Image[] cardImages = new Image[4*SUIT_SIZE];
	private Stage myStage;
	
	Deck deck;
	int handSize;
	ListView<Card> listView = new ListView<>();
	ObservableList<Card> items = listView.getItems();
	Button dealBtn = new Button("Deal Hand");
	Button fishBtn = new Button("Fish Sort");
	Button spadeBtn = new Button("Spade Sort");
	CheckBox cb = new CheckBox("Reverse Sort");

	public CardDisplayWindow(Stage stage, int size)
	{
		class DealHand implements EventHandler<ActionEvent>
		{	
			@Override
			public void handle(ActionEvent event) 
			{
				show(deck);
			}
		}//END class DealHand
		
		handSize = size;
		myStage = stage;
		myStage.setTitle("Card Hand");
		BorderPane pane = new BorderPane();
		HBox hb = new HBox();
		hb.setSpacing(20);
		hb.setAlignment(Pos.CENTER);
		pane.setBottom(hb);
		Scene scene = new Scene(pane);
		myStage.setScene(scene);
		myStage.setHeight(150);
		myStage.setWidth(68 * handSize);
		
		loadImages();
		
		hb.getChildren().addAll(dealBtn, fishBtn, spadeBtn, cb);
		cb.setIndeterminate(false);
		
		listView.setCellFactory(param -> new ListCell<Card>() 
		{
			private ImageView imageView = new ImageView();

			@Override
			public void updateItem(Card card, boolean empty)
			{
				super.updateItem(card, empty);
				
				if (empty) 
				{
					setGraphic(null);
				} 
				else
				{
					// determine the index of the card
					int index = card.getSuit() * SUIT_SIZE + card.getRank();
					imageView.setImage(cardImages[index]);
					imageView.setPreserveRatio(true);
					imageView.setFitWidth(50);
					setGraphic(imageView);
				}
			}
		});

		listView.setOrientation(Orientation.HORIZONTAL);
		pane.setCenter(listView);
		
		dealBtn.setOnAction(new DealHand());
		
		fishBtn.setOnAction(e -> Collections.sort(items));
		
		spadeBtn.setOnAction(e -> Collections.sort(items, new Card.CardCompare()));
		
		cb.setOnAction(e -> {if (cb.isSelected() == true)
								spadeBtn.setOnAction(ex -> Collections.reverse(items));
							else 
								spadeBtn.setOnAction(ex -> Collections.sort(items, new Card.CardCompare()));
							});
	}

	private void loadImages()
	{
		String resourceDir = "file:resources/cardspng/";
		char[] suits = { 'c', 'd', 'h', 's' };
		char[] rank = { '2', '3', '4', '5', '6', '7', '8', '9', '0', 'j', 'q', 'k', 'a' };
		int slot = 0;
		
		// load images
		for (int s = 0; s < 4; s++) 
		{
			for (int r = 0; r < SUIT_SIZE; r++) 
			{
				String path = resourceDir + suits[s] + rank[r] + ".png";
				cardImages[slot] = new Image(path);
				slot++;
			}
		}
	}

	public void show(Deck deck)
	{
		this.deck = deck;
		
		if (deck != null)
			listView.getItems().setAll(deck.deal(handSize));
		
		myStage.show();
	}
}//END class HandDisplayWindow
