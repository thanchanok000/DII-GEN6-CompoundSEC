package   Class;

import java.util.ArrayList;
import java.util.List;

public class CardEditHistoryList {
    private static List<CardEditHistory> historyList = new ArrayList<>();

    public static void addHistory(CardEditHistory history) {
        historyList.add(history);
    }

    public static List<CardEditHistory> getHistoryList() {
        return historyList;
    }
}
