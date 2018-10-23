/*
 * Class to build, 
 * shuffle and show 
 * the deck of playing 
 * cards.
 * 
 */

package edu.tridenttech.cpt237.cards.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.tridenttech.cpt237.cards.model.Card.CardCompare;

public class Deck
{
	// list of cards still in the deck
	private ArrayList<Card> deck = new ArrayList<>();

	// list of cards being used
	private ArrayList<Card> used = new ArrayList<>();

	// used to shuffle the deck
	Random dealer = new Random();
	
	public Deck()
	{
		// builds the deck
		for (int i = 0; i < 52; i++) 
		{
			deck.add(new Card(i));
		}
	}
	
	public ArrayList<Card> deal(int handSize)
	{
		ArrayList<Card> hand = new ArrayList<>();
		// do we need more cards?  If so, shuffle
		if (deck.size() < handSize) 
		{
			shuffle();
		}

		for (int i=0; i < handSize; i++) 
		{
			Card next = deck.remove(deck.size() - 1);
			hand.add(next);
			used.add(next);
		}
		
		return hand;
	}
	
	public void shuffle()
	{
		deck.addAll(used);
		for (int i=0; i < deck.size() - 1; i++) 
		{
			int swap = dealer.nextInt(deck.size() - i) + i;
			if (swap != i)
			{
				Card tmp1 = deck.get(i);
				Card tmp2 = deck.get(swap);
				deck.set(i, tmp2);
				deck.set(swap, tmp1);
			}
		}
	}
	
	// just used for testing
	public static void showHand(ArrayList<Card> hand)
	{
		for (Card c : hand) 
		{
			System.out.printf(" %s",c);
		}
		System.out.println();
	}
	
	// just used for testing
	public static void main(String args[])
	{
		Deck deck = new Deck();
		CardCompare cardComp = new CardCompare();

		deck.shuffle();
		ArrayList<Card> hand = deck.deal(5);
		Collections.sort(hand);
		showHand(hand);

		deck.shuffle();
		hand = deck.deal(5);
		Collections.sort(hand, cardComp);
		showHand(hand);
	}
}//END class Deck
