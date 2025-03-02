package     Class;

import java.util.List;

public class OwnerCard extends Card {

    public OwnerCard(int id, String ownerName) {
        super(id, ownerName, "high");
    }

    public void create(Card card) {
        System.out.println("Owner Card Created: " + card);
    }

    public Card read(int id) {
        return null;
    }

    public Card update(Card card) {
        return null;
    }

    public boolean delete(int id) {
        return false;
    }

    public List<Card> getAll() {
        return List.of();
    }

    @Override
    public String getAccessLevel() {
        return "high";
    }
}
