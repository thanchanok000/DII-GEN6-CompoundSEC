package   Class;

import java.util.ArrayList;
import java.util.List;

public class CardList {
    private static final List<Card> cards = new ArrayList<>();

    // เพิ่ม Card ไปที่ List
    public static void addCard(Card card) {
        cards.add(card);
        System.out.println("Card Added: " + card);
    }



    // Update Card in the List
    public static void updateCard(int index, Card newCard) {
        Card oldCard = cards.get(index);
        cards.set(index, newCard);
        System.out.println("Card Updated: " + newCard);

        // Save history of card update
        CardEditHistoryList.addHistory(new CardEditHistory(oldCard, "update", oldCard.toString(), newCard.toString()));
    }

    // Retrieve all cards
    public static List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    // Display cards with encrypted access level
    public static void displayCards() {
        for (Card card : cards) {
            System.out.println("Card [ID=" + card.getId() + ", Name=" + card.getOwnerName() + ", Access Level=" + card.getEncryptedAccessLevel() + "]");
        }
       }
}
