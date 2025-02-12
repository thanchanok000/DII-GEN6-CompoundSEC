package Class;

public abstract class Card {
    private int id;
    private String ownerName;
    private String accessLevel;

    public Card(int id, String ownerName, String accessLevel ) {
        this.id = id;
        this.ownerName = ownerName;
        this.accessLevel = accessLevel;
    }

    public int getId() {
        return id;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    @Override
    public String toString() {
        return "Card [ID=" + id + ", Name=" + ownerName + ", Access Level=" + accessLevel + "]";
    }

    public String getOwnerName() {
        return ownerName;
    }
}
