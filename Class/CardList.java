package Class;

import java.util.ArrayList;
import java.util.List;

public class CardList {
    // Private static instance
    private static CardList instance;
    private static final List<Card> cards = new ArrayList<>();

    // Private constructor to prevent instantiation
    private CardList() {
        // Initialize the list or perform any setup needed
    }

    // Public static method to get the singleton instance
    public static CardList getInstance() {
        if (instance == null) {
            instance = new CardList(); // Initialize only once
        }
        return instance;
    }

    // Methods for adding, updating, and removing cards
    public void addCard(Card card) {
        cards.add(card);
        System.out.println("Card Added: " + card);
    }

    public void updateCard(int index, Card newCard) {
        Card oldCard = cards.get(index);
        cards.set(index, newCard);
        System.out.println("Card Updated: " + newCard);

        // Save history of card update
        CardEditHistoryList.addHistory(new CardEditHistory(oldCard, "update", oldCard.toString(), newCard.toString()));
    }

    public static List<Card> getCards() {
        return new ArrayList<>(cards); // Return a copy of the list
    }

    public void displayCards() {
        for (Card card : cards) {
            System.out.println("Card [ID=" + card.getId() + ", Name=" + card.getOwnerName() + ", Access Level=" + card.getEncryptedAccessLevel() + "]");
        }
    }
}
