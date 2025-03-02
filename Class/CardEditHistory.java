package    Class;

import java.time.LocalDateTime;

public class CardEditHistory {
    private Card card;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime timestamp;

    public CardEditHistory(Card card, String fieldName, String oldValue, String newValue) {
        this.card = card;
        this.fieldName = fieldName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.timestamp = LocalDateTime.now();
    }

    public Card getCard() {
        return card;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "CardEditHistory [card=" + card + ", fieldName=" + fieldName + ", oldValue=" + oldValue + ", newValue=" + newValue + ", timestamp=" + timestamp + "]";
    }
}
