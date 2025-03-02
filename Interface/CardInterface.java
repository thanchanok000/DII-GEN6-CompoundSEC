package Interface;
import Class.Card;

import java.util.List;



public interface CardInterface {
    void create(Card card);
    Card read(int id);
    Card update(Card card);
    boolean delete(int id);
    List<Card> getAll();
}
