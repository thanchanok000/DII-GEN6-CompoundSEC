package Class;

import java.util.ArrayList;
import java.util.List;

public class RoomList {
    private static final List<Room> rooms = new ArrayList<>();

    public static void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Room Added: " + room);
    }

    public static void addRoom(int id, String name, List<String> accessLevel) {
        Room room = new Room(id, name, accessLevel);
        addRoom(room);
    }

    public static List<Room> getRooms() {
            return new ArrayList<>(rooms);
    }

    public static void setRooms(List<Room> roomList) {
        rooms.clear();
        rooms.addAll(roomList) ;
    }
}
