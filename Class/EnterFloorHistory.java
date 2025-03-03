package    Class;

import java.time.LocalDateTime;


// โดยใน Class นี้จะเป็นการบันทึกประวัติการเข้าชั้นของบัตร
public class EnterFloorHistory {
    private Card card;
    private boolean success;
    private LocalDateTime timestamp;
    private String floorName;

    public EnterFloorHistory(Card card, boolean success, String floorName) {
        this.card = card;
        this.success = success;
        this.floorName = floorName;
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

    public String getFloorName() {
        return floorName;
    }

    @Override
    public String toString() {
        return "EnterFloorHistory [Card=" + card + ", Success=" + success + ", FloorName=" + floorName + ", Timestamp=" + timestamp + "]";
    }
}