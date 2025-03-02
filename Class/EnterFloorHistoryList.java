package    Class;

import java.util.ArrayList;
import java.util.List;

public class EnterFloorHistoryList {
    private static final List<EnterFloorHistory> histories = new ArrayList<>();

    public static void addHistory(EnterFloorHistory history) {
        histories.add(history);
        System.out.println("History Added: " + history);
    }

    public static List<EnterFloorHistory> getHistories() {
        return new ArrayList<>(histories);
    }
}