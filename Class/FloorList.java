package    Class;

import java.util.ArrayList;
import java.util.List;

public class FloorList {
    private static final List<Floor> floors = new ArrayList<>();
    private static int currentId = 0;

    // เพิ่ม Floor เข้าไปที่ List
    public static void addCard(Floor floor) {
        floor.setId(currentId++);
        floors.add(floor);
        System.out.println("Floor Added: " + floor);
    }

    public static List<Floor> getFloors() {
        return new ArrayList<>(floors);
    }

}
