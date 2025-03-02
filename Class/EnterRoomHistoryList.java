package    Class;

import java.util.ArrayList;
import java.util.List;

public class EnterRoomHistoryList {
    private static final List<EnterRoomHistory> histories = new ArrayList<>();

    public static void addHistory(EnterRoomHistory history) {
        histories.add(history);
        System.out.println("History Added: " + history);
    }

    public static List<EnterRoomHistory> getHistories() {
        return new ArrayList<>(histories);
    }
}
