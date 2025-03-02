package    Class;

import java.util.Arrays;
import java.util.List;

public class ChildFloor extends Floor {
    public ChildFloor(int id, String roomName, List<Room> rooms) {
        super(id, roomName, Arrays.asList("low"), rooms);
    }

    @Override
    public boolean getAccess(Card card) {
        return false;
    }

    // Late Binding
    @Override
    public void addRoom(Room room) {
        getRooms().add(room);
    }
}
