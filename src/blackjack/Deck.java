package blackjack;

import java.util.ArrayList;
import java.util.Collections;

import static blackjack.Values.ACE;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createFullDeck() {
        for (Suits suit : Suits.values()) {
            for (Values value : Values.values()){
                this.deck.add(new Card(suit, value));
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public Card getCard(int i){
        return this.deck.get(i);
    }

    public void removeCard(int i){
        this.deck.remove(i);
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    // Get the size of the deck
    public int deckSize() {
        return this.deck.size();
    }

    // Draws from the deck
    public void draw(Deck comingFrom) {
        this.deck.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    // This will move cards back into the deck to continue playing
    public void moveAllToDeck(Deck moveTo) {
        for (int i = 0; i < this.deckSize(); i++){
            moveTo.addCard(this.getCard(i));
            this.removeCard(0);
        }
    }

    public int getValue(){
        int total = 0;
        int aces = 0;

        for (Card card : deck) {
            if (card.getValue().equals(ACE)){
                aces += 1;
                continue;
            }
            total += card.getIntValue();
        }

        for (int i = 0; i < aces; i++){
            if (total > 10){
                total += 1;
            } else {
                total += 11;
            }
        }
        return total;
    }

    public void printHand() {
        for (Card card : deck){
            System.out.print("[" + card + "] ");
        }
        System.out.println("The value is " + this.getValue());
    }
}