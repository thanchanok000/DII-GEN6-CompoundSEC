package    Class;

import java.time.LocalDateTime;

public class EnterRoomHistory {
    private Card card;
    private boolean success;
    private LocalDateTime timestamp;
    private String roomName;

    public EnterRoomHistory(Card card, boolean success, String roomName) {
        this.card = card;
        this.success = success;
        this.roomName = roomName;
        this.timestamp = LocalDateTime.now();
    }

    public Card getCard() {
        return card;
    }

    public boolean isSuccess() {
        return success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return "EnterRoomHistory [Card=" + card + ", Success=" + success + ", RoomName=" + roomName + ", Timestamp=" + timestamp + "]";
    }
}
