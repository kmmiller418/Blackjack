package blackjack;

import java.util.Scanner;

public class Blackjack {
    private static Scanner s = new Scanner(System.in);
    static boolean exitGame = false;

    public static void main(String[] args) {
        playGame();
    }
    public static void playGame() {
        System.out.println("Welcome to Blackjack!");
        int playerPot;

        while(true){
            System.out.println("How much money are you playing with today?");
            playerPot = Integer.parseInt(s.nextLine());

            if (playerPot < 5) {
                System.out.println("Sorry, the table minimum is 5.");
                continue;
            }
            break;
        }

        while (!exitGame && playerPot != 0) {
            playerPot = playRound(playerPot);
        }

        if (playerPot == 0) {
            System.out.println("You seem to be out of money... ");
            System.out.println("Come back later!");
        } else {
            System.out.println("Thanks for playing");
            System.out.println("You walked away with $" + playerPot);
        }

    }

    public static int playRound(int playerPot) {
        //each round utilizes new deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();

        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        int bet;

        while (true){
            System.out.println("You currently have $" + playerPot);
            System.out.println("How much do you want to bet? Enter your bet (must be made in increments of $5) or 0 to walk away");
            bet = Integer.parseInt(s.nextLine());

            if (bet %5 != 0) {
                System.out.println("Sorry, bets can only be made in increments of $5.");
                continue;
            }

            if (bet > playerPot) {
                System.out.println("Sorry, that's more than you currently have. Please enter a different amount.");
                continue;
            }

            if ( bet == 0 ) {
                exitGame = true;
                return playerPot;
            }
            break;
        }

        // deal cards
        playerHand.draw(playingDeck);
        dealerHand.draw(playingDeck);
        playerHand.draw(playingDeck);
        dealerHand.draw(playingDeck);

        System.out.println("Your hand:");
        playerHand.printHand();
        System.out.println("Dealer's hand:");
        System.out.println(dealerHand.getCard(0).toString() + " []");

        System.out.println("Would you like to double down? (y/n)");
        String response = s.nextLine();

        if (response.equalsIgnoreCase("y")){
            if (bet * 2 > playerPot) {
                System.out.println("Sorry, you don't have enough money to double down. This round will continue with your initial bet.");
            } else {
                bet *= 2;
            }
        }

        while (true) {
            System.out.println("Do you want to hit? (y/n)");
            response = s.nextLine();

            if (response.equalsIgnoreCase("y")) {
                playerHand.draw(playingDeck);
                System.out.println("You drew a " + playerHand.getCard(playerHand.deckSize() - 1));
                System.out.println("Your hand is now:");
                playerHand.printHand();
                if (playerHand.getValue() > 21) {
                    System.out.println("Shoot, you bust.");
                    //bust automatically loses
                    System.out.println("The dealer hand was: ");
                    dealerHand.printHand();
                    playerPot -= bet;
                    System.out.println("You lost $" + bet + " and now have $" + playerPot);
                    return playerPot;
                }
            } else {
                break;
            }
        }


        System.out.println("Your final hand is:");
        playerHand.printHand();

        while (true) {
            //natural 21 auto-wins unless dealer also has natural 21
            if (dealerHand.getValue() >= 17 || (playerHand.getValue() == 21 && playerHand.deckSize() == 2)) {
                break;
            }

            dealerHand.draw(playingDeck);
            System.out.println("The dealer's hand is:");
            dealerHand.printHand();
            System.out.println("The dealer drew a " + dealerHand.getCard(dealerHand.deckSize() - 1));

            if (dealerHand.getValue() > 21) {
                System.out.println("Lucky break! The dealer busts!");
                playerPot += bet;
                System.out.println("You gained $" + bet + " and now have $" + playerPot);
                return playerPot;
            }
        }

        if (dealerHand.getValue() > playerHand.getValue()) {
            System.out.println("The dealer's hand is:");
            dealerHand.printHand();
            System.out.println("Too bad! The dealer beat you by " + (dealerHand.getValue() - playerHand.getValue()));
            playerPot -= bet;
            System.out.println("You lost $" + bet + " and now have $" + playerPot);
        } else if (playerHand.getValue() > dealerHand.getValue()){
            System.out.println("Nice one! Your hand value of " + playerHand.getValue() + " sure trumped in the end");
            playerPot += bet;
            System.out.println("You gained $" + bet + " and now have $" + playerPot);
        } else {
            System.out.println("Wow, a tie. What are the odds? Keep your money for the next round.");
        }
        return playerPot;
    }
}
