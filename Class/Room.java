package  Class;

import java.util.List;

public class Room {
    private int id;
    private String name;
    private List<String> accessableRole;

    public Room(int id, String name, List<String> accessableRole) { // Modify constructor
        this.id = id;
        this.name = name;
        this.accessableRole = accessableRole; // Initialize accessLevel
    }
//สามารถแก้ไขชื่อห้อง และสิทธิ์ของห้องได้ (setName() & setAccessLevel())
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAccessLevel() {
        return accessableRole;
    }

    public void setAccessLevel(List<String> accessLevel) {
        this.accessableRole.clear();
        this.accessableRole.addAll(accessLevel);
    }
 //ตรวจสอบสิทธิ์ของบัตร enterRoom()
    public boolean enterRoom(Card card) {
        for (String role : this.accessableRole) {
            if (card.getAccessLevel().toLowerCase().equals((role).toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accessableRole=" + accessableRole +
                '}';
    }

}
