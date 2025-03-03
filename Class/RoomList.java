package Class;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
    //Private static instance
    private static RoomList instance;
    private static final List<Room> rooms = new ArrayList<>();

    //Private constructor to prevent instantiation
    private RoomList() {
        // Initialize the list or perform any setup needed
    }

    //Public static method to get the singleton instance
    public static RoomList getInstance() {
        if (instance == null) {
            instance = new RoomList(); // Initialize only once
        }
        return instance;
    }

    // Methods for adding, updating, and removing rooms
    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room Added: " + room);
    }

    public void addRoom(int id, String name, List<String> accessLevel) {
        Room room = new Room(id, name, accessLevel);
        addRoom(room);
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms); // Return a copy of the list
    }

    public void setRooms(List<Room> roomList) {
        rooms.clear();
        rooms.addAll(roomList);
    }
}
