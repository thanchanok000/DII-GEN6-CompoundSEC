package Class;

import java.util.List;

public class EmployeeCard {
    private int id;
    private String ownerName;
    private String accessLevel;

    public EmployeeCard(int id, String ownerName, String accessLevel ) {
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

}
