package blackjack;

import java.util.Random;

public class Card {

    private Suits suit;
    private Values value;

    // create arrays for values and suits - need these to generate random cards
    private Values[] values = Values.values();
    private Random randomValues = new Random();
    private Suits[] suits = Suits.values();
    private Random randomSuits = new Random();

    public Card(Suits suit, Values value) {
        this.value = value;
        this.suit = suit;
    }

    // random card generated
    public Card() {
        this.suit = getRandomSuit();
        this.value = getRandomValue();
    }

    @Override
    public String toString() {
        return this.suit.toString() + "-" + this.value.toString();
    }

    public Values getValue() {
        return this.value;
    }

    public int getIntValue() {
        return this.value.value;
    }

    public Values getRandomValue() {
        return values[randomValues.nextInt(values.length)];
    }

    public Suits getRandomSuit() {
        return suits[randomSuits.nextInt(values.length)];
    }
}