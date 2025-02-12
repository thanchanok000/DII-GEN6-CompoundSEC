package  Class;
import  Class.Card;
import java.util.List;

public class EmployeeCard extends Card {

    public EmployeeCard(int id, String ownerName) {
        super(id, ownerName, "low");
    }

    public void create(Card card) {
        System.out.println("Employee Card Created: " + card);
    }

    public Card read(int id) {
        return null;
    }

    public Card update(Card card) {
        return null;
    }

    public void updateAccessLevel( String level) {
        setAccessLevel(level);
    }

    public boolean delete(int id) {
        return false;
    }

    public List<Card> getAll() {
        return List.of();
    }

}
