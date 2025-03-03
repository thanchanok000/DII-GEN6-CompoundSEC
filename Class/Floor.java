package Class;

import java.util.ArrayList;
import java.util.List;

public abstract class Floor {
    private int id;
    private String roomName;
    private List<String> accessableRole;
    private List<Room> rooms;
    private List<EnterRoomHistory> enterRoomHistory;


    public Floor(int id, String roomName, List<String> accessableRole, List<Room> rooms) {
        this.id = id;
        this.roomName = roomName;
        this.accessableRole = accessableRole;
        this.rooms = rooms;
        this.enterRoomHistory = new ArrayList<>(); // Initialize the history list
    }

    public abstract boolean getAccess(Card card);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return roomName;
    }

    public List<String> getPriority() {
        return accessableRole;
    }

    public void setPriority(List<String> accessableRole) {
        this.accessableRole = accessableRole;
    }

    protected List<String> getAccessableRole() {
        return accessableRole;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    // ฟังก์ชันเข้าห้อง โดยใส่ Card ในพารามิเตอร์
    // ถ้าผ่านจะบันทึกเป็น เข้าห้องสำเร็จ ถ้าไม่ ก็บันทึกเช่นกัน แต่จะบันทึกว่า เข้าห้องไม่สำเร็จ
    //ข้อมูลการเข้าชั้น (ทั้งสำเร็จและไม่สำเร็จ) จะถูกบันทึกใน enterRoomHistory
    public boolean enterFloor(Card card) {
        boolean accessGranted = false;
        String decryptedAccessLevel = card.getAccessLevel(); // Decrypt access level
        for (String role : this.accessableRole) {
            if (decryptedAccessLevel.toLowerCase().equals(role.toLowerCase())) {
                accessGranted = true;
                break;
            }
        }
        this.enterRoomHistory.add(new EnterRoomHistory(card, accessGranted, roomName));
        return accessGranted;
    }

    // Late Binding
    public abstract void addRoom(Room room);

    // List ที่จัดเก็บประวัติของการเข้าห้องในชั้นนั้นๆ
       public List<EnterRoomHistory> getEnterRoomHistory() {
        return enterRoomHistory;
    }

    // ใช้ในการเก็บประวัติการเข้าห้อง
    public void addEnterRoomHistory(EnterRoomHistory history) {
        System.out.println("Adding history: " + history);
        this.enterRoomHistory.add(history);
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", roomName='" + roomName + '\'' +
                ", accessableRole=" + accessableRole +
                ", rooms=" + rooms +
                ", enterRoomHistory=" + enterRoomHistory +
                '}';
    }
}
