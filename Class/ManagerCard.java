package Class;

import java.util.List;

public class ManagerCard extends Card    {

    public ManagerCard(int id, String ownerName ) {
        super(id, ownerName, "medium");
    }

    public void create(Card card) {
        System.out.println("Manager Card Created: " + card);
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

}
